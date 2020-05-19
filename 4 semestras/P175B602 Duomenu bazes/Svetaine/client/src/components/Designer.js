import React from 'react';
import TextField from '@material-ui/core/TextField';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Grid from '@material-ui/core/Grid';
import { useDispatch, useSelector } from 'react-redux';
import { getDesigner } from '../store/selectors/designer';
import { updateDesigner } from '../store/actions/designer';
import Typography from '@material-ui/core/Typography';

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

function Designer() {
  const classes = useStyles();
  const dispatch = useDispatch();

  const designer = useSelector(getDesigner);

  return (
    <>
      <Typography align={'center'} className={classes.title}>
        Designer information
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
                  value={designer.name}
                  onChange={(event) => dispatch(updateDesigner('name', event.target.value))}
                  className={classes.form}
                />

                <TextField
                  required
                  type={'text'}
                  label={'Surname'}
                  value={designer.surname}
                  onChange={(event) => dispatch(updateDesigner('surname', event.target.value))}
                  className={classes.form}
                />

                <TextField
                  required
                  type={'text'}
                  label={'Country'}
                  value={designer.country}
                  onChange={(event) => dispatch(updateDesigner('country', event.target.value))}
                  className={classes.form}
                />
              </Grid>

              <Grid item>
                <TextField
                  required
                  type={'number'}
                  inputProps={{ step: '1', min: '0' }}
                  label={'Awards won'}
                  value={designer.awards_won}
                  onChange={(event) => dispatch(updateDesigner('awards_won', event.target.value))}
                  className={classes.form}
                />

                <TextField
                  required
                  type={'date'}
                  InputLabelProps={{ shrink: true }}
                  label={'Birthday'}
                  value={designer.birthday}
                  onChange={(event) => dispatch(updateDesigner('birthday', event.target.value))}
                  className={classes.form}
                />
              </Grid>
            </Grid>
          </CardContent>
        </Card>
      </Grid>
    </>
  );
}

export default Designer;
