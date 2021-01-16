const { start, dispatch, stop, spawn, spawnStateless } = require('nact');
const fs = require('fs');
const INPUT_FILE = './data/input_1.json';
const OUTPUT_FILE = './src/data/output_1.txt';


const cars = require(INPUT_FILE);
const workerCount = parseInt(cars.length / 4);
const system = start();
const MsgTypes = {
    INPUT: 'INPUT',
    RESULT: 'RESULT',
    GET_RESULT: 'GET_RESULT',
    SET_RESULT: 'SET_RESULT',
    PRINT_RESULT: 'PRINT_RESULT'
};

const spawnWorker = (parent, workerId) => spawnStateless(
    parent,
    (car, ctx) => {
        // Sum of car.model's char values
        const reducer = (sum, letter) => sum + parseInt(letter.charCodeAt());
        const stringSum = car.model.split('').reduce(reducer, 0);
        const result = (car.mileage * car.fuelEcon + stringSum).toFixed(2);

        // If the result is even, send it to the distributor.
        // Otherwise, just inform the distributor that work has been done.
        if (parseInt(result[0]) % 2 === 0)
            dispatch(parent, { type: MsgTypes.RESULT, payload: { car, result } });
        else
            dispatch(parent, { type: MsgTypes.RESULT });
    },
    workerId
);

const resultActor = spawn(
    system,
    (resultArray = [], msg, ctx) => {
        switch (msg.type) {
            case MsgTypes.GET_RESULT:
                dispatch(msg.sender, { type: MsgTypes.PRINT_RESULT, payload: resultArray });
                break;

            case MsgTypes.SET_RESULT:
                const index = resultArray.findIndex(
                    element => parseFloat(element.result) > parseFloat(msg.payload.result)
                );

                const insertIndex = index !== -1 ? index : resultArray.length;
                resultArray.splice(insertIndex, 0, msg.payload);
                break;
        }

        return resultArray;
    },
    'results'
);

const printerActor = spawnStateless(
    system,
    (resultArray, ctx) => {
        const header =
            '+-----------+----------+-------------+------------------+\n' +
            '|    ODO    |   Econ   |    Model    |    Calculated    |\n' +
            '+-----------+----------+-------------+------------------+\n';
        const rows =
            resultArray.reduce((lines, obj) => {
                return lines + `| ${obj.car.mileage.toString().padStart(9)} | ${obj.car.fuelEcon.toString().padStart(8)} | ${obj.car.model.padEnd(11)} | ${obj.result.padStart(16)} |\n`
            }, '');
        const footer =
            '+-----------+----------+-------------+------------------+\n';

        if (resultArray.length > 0) {
            fs.writeFile(OUTPUT_FILE, header + rows + footer, (err) => {
                if (err) throw err;
            });
        } else {
            fs.writeFile(OUTPUT_FILE, 'Table is empty', (err) => {
                if (err) throw err;
            });
        }

        console.log(`Result saved to ${OUTPUT_FILE}`);

        stop(system);
    },
    'printer'
);

const distributorActor = spawn(
    system,
    (state = { workSent: 0, workDone: 0 }, msg, ctx) => {
        switch (msg.type) {
            case MsgTypes.INPUT:
                const workerName = (state.workSent % workerCount).toString();

                if (ctx.children.has(workerName)) {
                    dispatch(ctx.children.get(workerName), msg.payload);
                } else {
                    const worker = spawnWorker(ctx.self, workerName);
                    dispatch(worker, msg.payload);
                }

                return {
                    ...state,
                    workSent: state.workSent + 1
                };

            case MsgTypes.RESULT:
                if (msg.payload)
                    dispatch(resultActor, { type: MsgTypes.SET_RESULT, payload: msg.payload });
                
                if (state.workDone + 1 === cars.length)
                    dispatch(resultActor, { type: MsgTypes.GET_RESULT, sender: ctx.self });

                return {
                    ...state,
                    workDone: state.workDone + 1
                };

            case MsgTypes.PRINT_RESULT:
                dispatch(printerActor, msg.payload);
                break;
        }

        return state;
    },
    'distributor'
);

cars.forEach(
    car => dispatch(distributorActor, { type: MsgTypes.INPUT, payload: car })
);
