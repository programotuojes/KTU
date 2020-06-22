import React from 'react';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import TableContainer from '@material-ui/core/TableContainer';
import Table from '@material-ui/core/Table';
import TableHead from '@material-ui/core/TableHead';
import TableBody from '@material-ui/core/TableBody';
import TableRow from '@material-ui/core/TableRow';
import TableCell from '@material-ui/core/TableCell';
import ArrowBackIcon from '@material-ui/icons/ArrowBack';
import Fab from '@material-ui/core/Fab';
import { makeStyles } from '@material-ui/core/styles';
import { useDispatch, useSelector } from 'react-redux';
import { resetReport } from '../store/actions/report';
import Typography from '@material-ui/core/Typography';
import { formatData, getAlignment, parseMoney } from '../utils/util';
import { getFilters } from '../store/selectors/report';

const useStyles = makeStyles({
  title: {
    marginTop: 30,
    marginBottom: 10,
    fontSize: '1.5em',
  },
  subtitle: {
    marginBottom: 10,
  },
  emptyMessage: {
    marginTop: 20,
    fontStyle: 'italic',
  },
  container: {
    maxHeight: '70vh',
  },
  header: {
    backgroundColor: 'lightgray',
    textTransform: 'capitalize',
    fontWeight: 'bold',
  },
  actions: {
    width: 100,
  },
  fab: {
    margin: 0,
    top: 'auto',
    right: 20,
    bottom: 20,
    left: 'auto',
    position: 'fixed',
  },
});

function ReportResults({ results }) {
  const classes = useStyles();
  const dispatch = useDispatch();

  const filters = useSelector(getFilters);

  const columns = results.length !== 0 ? Object.keys(results[0]) : [];

  return (
    <>
      <Fab color="primary" onClick={() => dispatch(resetReport())} className={classes.fab}>
        <ArrowBackIcon />
      </Fab>

      <Typography align={'center'} className={classes.title}>
        Report
      </Typography>
      <Typography align={'center'} className={classes.subtitle}>
        Order date: {filters.dateFrom} - {filters.dateTo}
        <br />
        Customer age: {filters.ageFrom} - {filters.ageTo}
        <br />
        Status: {filters.status}
      </Typography>

      {(results.length !== 0 && (
        <Grid container justify={'center'}>
          <Paper elevation={4}>
            <TableContainer className={classes.container}>
              <Table>
                <TableHead>
                  <TableRow>
                    {columns.map((cell, index) => (
                      <TableCell align={'center'} className={classes.header} key={index}>
                        {cell}
                      </TableCell>
                    ))}
                  </TableRow>
                </TableHead>

                <TableBody>
                  {results.map((row, indexRow) => {
                    return (
                      <TableRow key={indexRow}>
                        {Object.values(row).map((cell, indexCell) => {
                          if (
                            indexCell < 4 &&
                            indexRow > 0 &&
                            results[indexRow - 1]['Customer ID'] === row['Customer ID']
                          )
                            return <TableCell key={indexCell} />;
                          else
                            return (
                              <TableCell key={indexCell} align={getAlignment(columns[indexCell])}>
                                {formatData(columns[indexCell], cell)}
                              </TableCell>
                            );
                        })}
                      </TableRow>
                    );
                  })}
                </TableBody>
              </Table>
            </TableContainer>
          </Paper>
        </Grid>
      )) || (
        <Typography align={'center'} className={classes.emptyMessage}>
          No data found with the current filters
        </Typography>
      )}
    </>
  );
}

export default ReportResults;
