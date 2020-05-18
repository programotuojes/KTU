import React, { useEffect, useState } from 'react';
import InputLabel from '@material-ui/core/InputLabel';
import Select from '@material-ui/core/Select';
import MenuItem from '@material-ui/core/MenuItem';
import TextField from '@material-ui/core/TextField';
import Grid from '@material-ui/core/Grid';
import { makeStyles } from '@material-ui/core/styles';
import FormControl from '@material-ui/core/FormControl';
import Typography from '@material-ui/core/Typography';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import { updateEmployee } from '../store/actions/employee';
import { useDispatch, useSelector } from 'react-redux';
import { getEmployee } from '../store/selectors/employee';
import { get } from '../utils/network';
import * as paths from '../utils/paths';

const useStyles = makeStyles({
  title: {
    marginTop: 30,
    marginBottom: 10,
    fontSize: '1.5em',
  },
  form: {
    width: 200,
    margin: 8,
  },
});

function Employee() {
  const classes = useStyles();
  const dispatch = useDispatch();

  const employee = useSelector(getEmployee);
  const [employees, setEmployees] = useState([]);
  const [shops, setShops] = useState([]);

  useEffect(() => {
    get(paths.EMPLOYEE_NAMES, setEmployees);
    get(paths.SHOP_NAMES, setShops);
  }, []);

  return (
    <>
      <Typography align={'center'} className={classes.title}>
        Employee information
      </Typography>

      <Grid container justify={'center'}>
        <Card elevation={4}>
          <CardContent>
            <Grid container direction={'column'} alignItems={'center'}>
              <Grid item>
                <TextField
                  required
                  type={'text'}
                  label={'Name'}
                  value={employee.name}
                  onChange={(event) => dispatch(updateEmployee('name', event.target.value))}
                  className={classes.form}
                />

                <TextField
                  required
                  type={'text'}
                  label={'Surname'}
                  value={employee.surname}
                  onChange={(event) => dispatch(updateEmployee('surname', event.target.value))}
                  className={classes.form}
                />

                <TextField
                  required
                  type={'date'}
                  InputLabelProps={{ shrink: true }}
                  label={'Birthday'}
                  value={employee.birthday}
                  onChange={(event) => dispatch(updateEmployee('birthday', event.target.value))}
                  className={classes.form}
                />
              </Grid>

              <Grid item>
                <TextField
                  required
                  type={'number'}
                  label={'Phone number'}
                  value={employee.phone}
                  onChange={(event) => dispatch(updateEmployee('phone', event.target.value))}
                  className={classes.form}
                />

                <TextField
                  required
                  type={'text'}
                  label={'Bank account'}
                  value={employee.bank_account}
                  onChange={(event) => dispatch(updateEmployee('bank_account', event.target.value))}
                  className={classes.form}
                />

                <TextField
                  required
                  type={'text'}
                  label={'Address'}
                  value={employee.address}
                  onChange={(event) => dispatch(updateEmployee('address', event.target.value))}
                  className={classes.form}
                />
              </Grid>

              <Grid item>
                <FormControl className={classes.form}>
                  <InputLabel id={'reports-to-label'}>Reports to</InputLabel>
                  <Select
                    value={employee.reports_to}
                    onChange={(event) => dispatch(updateEmployee('reports_to', event.target.value))}
                    labelId={'reports-to-label'}
                  >
                    <MenuItem value={''}>{'None'}</MenuItem>
                    {employees.map((item, index) => (
                      <MenuItem value={item.id} key={index}>
                        {`${item.name} ${item.surname}`}
                      </MenuItem>
                    ))}
                  </Select>
                </FormControl>

                <FormControl className={classes.form}>
                  <InputLabel id={'shop-label'}>Shop</InputLabel>
                  <Select
                    value={employee.shop_id}
                    onChange={(event) => dispatch(updateEmployee('shop_id', event.target.value))}
                    labelId={'shop-label'}
                  >
                    <MenuItem value={''}>{'None'}</MenuItem>
                    {shops.map((item, index) => (
                      <MenuItem key={index} value={item.id}>
                        {`${item.country}, ${item.city}, ${item.address}`}
                      </MenuItem>
                    ))}
                  </Select>
                </FormControl>
              </Grid>
            </Grid>
          </CardContent>
        </Card>
      </Grid>
    </>
  );
}

export default Employee;
