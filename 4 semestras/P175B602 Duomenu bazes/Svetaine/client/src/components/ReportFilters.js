import React from 'react';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Grid from '@material-ui/core/Grid';
import TextField from '@material-ui/core/TextField';
import { makeStyles } from '@material-ui/core/styles';
import { useDispatch, useSelector } from 'react-redux';
import { setErrors, showResults, updateFilter } from '../store/actions/report';
import Typography from '@material-ui/core/Typography';
import InputLabel from '@material-ui/core/InputLabel';
import Select from '@material-ui/core/Select';
import MenuItem from '@material-ui/core/MenuItem';
import FormControl from '@material-ui/core/FormControl';
import { STATUS } from '../utils/constants';
import { getErrors, getFilters } from '../store/selectors/report';
import Button from '@material-ui/core/Button';
import FormHelperText from '@material-ui/core/FormHelperText';

const useStyles = makeStyles({
  title: {
    marginTop: 30,
    marginBottom: 10,
    fontSize: '1.5em',
  },
  label: {
    marginBottom: 10,
  },
  form: {
    width: 160,
    marginBottom: 8,
  },
});

function ReportFilters() {
  const classes = useStyles();
  const dispatch = useDispatch();

  const filters = useSelector(getFilters);
  const errors = useSelector(getErrors);

  function validate() {
    let valid = true;
    let errors = {
      dateFrom: '',
      dateTo: '',
      ageFrom: '',
      ageTo: '',
      status: '',
    };

    if (!filters.dateFrom) {
      errors.dateFrom = 'Enter a date';
      valid = false;
    }

    if (!filters.dateTo) {
      errors.dateTo = 'Enter a date';
      valid = false;
    }

    if (!filters.ageFrom) {
      errors.ageFrom = 'Enter an age';
      valid = false;
    }

    if (!filters.ageTo) {
      errors.ageTo = 'Enter an age';
      valid = false;
    }

    if (!filters.status) {
      errors.status = 'Select a status';
      valid = false;
    }

    dispatch(setErrors(errors));

    return valid;
  }

  function submit() {
    if (validate()) {
      dispatch(showResults(filters));
    }
  }

  return (
    <>
      <Typography align={'center'} className={classes.title}>
        Report filters
      </Typography>

      <Grid container justify={'center'}>
        <Card elevation={4}>
          <CardContent>
            <Grid container justify={'center'} direction={'column'} spacing={2}>
              <Grid item container alignItems={'flex-start'} justify={'center'} spacing={4}>
                <Grid item xs container justify={'center'} direction={'column'}>
                  <Typography align={'center'} className={classes.label}>
                    Order date
                  </Typography>

                  <TextField
                    required
                    type={'date'}
                    InputLabelProps={{ shrink: true }}
                    label={'From'}
                    onChange={(event) => dispatch(updateFilter('dateFrom', event.target.value))}
                    className={classes.form}
                    error={Boolean(errors.dateFrom)}
                    helperText={errors.dateFrom || ' '}
                  />

                  <TextField
                    required
                    type={'date'}
                    InputLabelProps={{ shrink: true }}
                    label={'To'}
                    onChange={(event) => dispatch(updateFilter('dateTo', event.target.value))}
                    className={classes.form}
                    error={Boolean(errors.dateTo)}
                    helperText={errors.dateTo || ' '}
                  />
                </Grid>

                <Grid item xs container justify={'center'} direction={'column'}>
                  <Typography align={'center'} className={classes.label}>
                    Customer age
                  </Typography>

                  <TextField
                    required
                    type={'number'}
                    inputProps={{ min: 0, step: 1 }}
                    label={'From'}
                    onChange={(event) => dispatch(updateFilter('ageFrom', event.target.value))}
                    className={classes.form}
                    error={Boolean(errors.ageFrom)}
                    helperText={errors.ageFrom || ' '}
                  />

                  <TextField
                    required
                    type={'number'}
                    inputProps={{ min: 0, step: 1 }}
                    label={'To'}
                    onChange={(event) => dispatch(updateFilter('ageTo', event.target.value))}
                    className={classes.form}
                    error={Boolean(errors.ageTo)}
                    helperText={errors.ageTo || ' '}
                  />
                </Grid>

                <Grid item xs container justify={'center'} direction={'column'}>
                  <Typography align={'center'} className={classes.label}>
                    Order status
                  </Typography>

                  <FormControl required className={classes.form} error={Boolean(errors.status)}>
                    <InputLabel id={'status-label'}>Status</InputLabel>
                    <Select
                      value={filters.status}
                      onChange={(event) => dispatch(updateFilter('status', event.target.value))}
                      labelId={'status-label'}
                    >
                      {STATUS.map((item, index) => (
                        <MenuItem value={item} key={index}>
                          {item}
                        </MenuItem>
                      ))}
                    </Select>
                    <FormHelperText>{errors.status || ' '}</FormHelperText>
                  </FormControl>
                </Grid>
              </Grid>

              <Grid item container justify={'center'}>
                <Button variant={'contained'} onClick={submit}>
                  Continue
                </Button>
              </Grid>
            </Grid>
          </CardContent>
        </Card>
      </Grid>
    </>
  );
}

export default ReportFilters;
