import React from 'react';
import TextField from '@material-ui/core/TextField';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Grid from '@material-ui/core/Grid';
import MenuItem from '@material-ui/core/MenuItem';
import FormControl from '@material-ui/core/FormControl';
import InputLabel from '@material-ui/core/InputLabel';
import Select from '@material-ui/core/Select';
import { useDispatch, useSelector } from 'react-redux';
import { getDesignerClothingById } from '../store/selectors/designerClothes';
import { updateDesignerClothing } from '../store/actions/designerClothes';
import { TYPES } from '../utils/constants';

const useStyles = makeStyles({
  root: {
    marginBottom: 20,
  },
  form: {
    width: 200,
    margin: 8,
  },
});

function Clothes({ styles, providers, designers }) {
  const classes = useStyles();
  const dispatch = useDispatch();

  const index = 0;

  const clothing = useSelector(getDesignerClothingById(index));

  return (
    <Grid container justify={'center'} className={classes.root}>
      <Card elevation={4}>
        <CardContent>
          <Grid container direction={'row'} alignItems={'center'}>
            <Grid item>
              <Grid item>
                <TextField
                  required
                  type={'text'}
                  label={'Name'}
                  value={clothing.name}
                  onChange={(event) =>
                    dispatch(updateDesignerClothing(index, 'name', event.target.value))
                  }
                  className={classes.form}
                />

                <TextField
                  required
                  type={'text'}
                  label={'Description'}
                  value={clothing.description}
                  onChange={(event) =>
                    dispatch(updateDesignerClothing(index, 'description', event.target.value))
                  }
                  className={classes.form}
                />

                <FormControl required className={classes.form}>
                  <InputLabel id={'type-label'}>Type</InputLabel>
                  <Select
                    value={clothing.type}
                    onChange={(event) =>
                      dispatch(updateDesignerClothing(index, 'type', event.target.value))
                    }
                    labelId={'type-label'}
                  >
                    {TYPES.map((item, index) => (
                      <MenuItem value={item} key={index}>
                        {item}
                      </MenuItem>
                    ))}
                  </Select>
                </FormControl>
              </Grid>

              <Grid item>
                <TextField
                  required
                  type={'number'}
                  inputProps={{ step: '0.01', min: '0.01' }}
                  label={'MSRP'}
                  value={clothing.msrp}
                  onChange={(event) =>
                    dispatch(updateDesignerClothing(index, 'msrp', event.target.value))
                  }
                  className={classes.form}
                />

                <TextField
                  required
                  type={'number'}
                  inputProps={{ step: '0.01', min: '0.01' }}
                  label={'Buy price'}
                  value={clothing.buy_price}
                  onChange={(event) =>
                    dispatch(updateDesignerClothing(index, 'buy_price', event.target.value))
                  }
                  className={classes.form}
                />

                <TextField
                  type={'number'}
                  inputProps={{ min: '0' }}
                  label={'Quantity in stock'}
                  value={clothing.quantity_in_stock}
                  onChange={(event) =>
                    dispatch(updateDesignerClothing(index, 'quantity_in_stock', event.target.value))
                  }
                  className={classes.form}
                />
              </Grid>

              <Grid item container justify={'center'}>
                <FormControl required className={classes.form}>
                  <InputLabel id={'designer-label'}>Designer</InputLabel>
                  <Select
                    value={clothing.designer_id}
                    onChange={(event) =>
                      dispatch(updateDesignerClothing(index, 'designer_id', event.target.value))
                    }
                    labelId={'designer-label'}
                  >
                    {designers.map((item, index) => (
                      <MenuItem value={item.id} key={index}>
                        {`${item.name} ${item.surname}`}
                      </MenuItem>
                    ))}
                  </Select>
                </FormControl>

                <FormControl required className={classes.form}>
                  <InputLabel id={'style-label'}>Style</InputLabel>
                  <Select
                    value={clothing.style_id}
                    onChange={(event) =>
                      dispatch(updateDesignerClothing(index, 'style_id', event.target.value))
                    }
                    labelId={'style-label'}
                  >
                    {styles.map((item, index) => (
                      <MenuItem value={item.id} key={index}>
                        {`${item.name} (${item.year})`}
                      </MenuItem>
                    ))}
                  </Select>
                </FormControl>

                <FormControl required className={classes.form}>
                  <InputLabel id={'provider-label'}>Provider</InputLabel>
                  <Select
                    value={clothing.provider_id}
                    onChange={(event) =>
                      dispatch(updateDesignerClothing(index, 'provider_id', event.target.value))
                    }
                    labelId={'provider-label'}
                  >
                    {providers.map((item, index) => (
                      <MenuItem value={item.id} key={index}>
                        {`${item.name} (${item.country})`}
                      </MenuItem>
                    ))}
                  </Select>
                </FormControl>
              </Grid>
            </Grid>
          </Grid>
        </CardContent>
      </Card>
    </Grid>
  );
}

export default Clothes;
