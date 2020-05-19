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
import { getOrderDetailsById, getUsedClothesIds } from '../store/selectors/orderDetails';
import { deleteOrderDetail, updateOrderDetail } from '../store/actions/orderDetails';
import IconButton from '@material-ui/core/IconButton';
import ClearIcon from '@material-ui/icons/Clear';

const useStyles = makeStyles({
  root: {
    marginBottom: 20,
  },
  form: {
    width: 200,
    margin: 8,
  },
});

function OrderDetails({ clothes, index }) {
  const classes = useStyles();
  const dispatch = useDispatch();

  const usedClothesIds = useSelector(getUsedClothesIds);
  const orderDetail = useSelector(getOrderDetailsById(index));

  const filteredClothes = clothes.filter(
    (item) => !usedClothesIds.includes(item.id) || item.id === orderDetail.clothes_id
  );

  return (
    <Grid container justify={'center'} className={classes.root}>
      <Card elevation={4}>
        <CardContent>
          <Grid container direction={'row'} alignItems={'center'}>
            <Grid item>
              <Grid item>
                <FormControl required className={classes.fonrm}>
                  <InputLabel id={'clothes-label'}>Clothes</InputLabel>
                  <Select
                    value={orderDetail.clothes_id}
                    onChange={(event) =>
                      dispatch(updateOrderDetail(index, 'clothes_id', event.target.value))
                    }
                    labelId={'clothes-label'}
                  >
                    {filteredClothes.map((item, index) => (
                      <MenuItem
                        value={item.id}
                        key={index}
                      >{`${item.name} (${item.type})`}</MenuItem>
                    ))}
                  </Select>
                </FormControl>

                <TextField
                  required
                  type={'number'}
                  inputProps={{ step: '1', min: '1' }}
                  label={'Quantity'}
                  value={orderDetail.quantity}
                  onChange={(event) =>
                    dispatch(updateOrderDetail(index, 'quantity', event.target.value))
                  }
                  className={classes.form}
                />

                <TextField
                  required
                  type={'number'}
                  inputProps={{ step: '0.01', min: '0' }}
                  label={'Price each'}
                  value={orderDetail.price_each}
                  onChange={(event) =>
                    dispatch(updateOrderDetail(index, 'price_each', event.target.value))
                  }
                  className={classes.form}
                />
              </Grid>
              <Grid item>
                <TextField
                  type={'number'}
                  inputProps={{ step: '0.01', min: '0' }}
                  label={'Money refunded'}
                  value={orderDetail.amount_refunded}
                  onChange={(event) =>
                    dispatch(updateOrderDetail(index, 'amount_refunded', event.target.value))
                  }
                  className={classes.form}
                />

                <TextField
                  type={'text'}
                  label={'Refund reason'}
                  value={orderDetail.refund_reason}
                  onChange={(event) =>
                    dispatch(updateOrderDetail(index, 'refund_reason', event.target.value))
                  }
                  className={classes.form}
                />
              </Grid>
            </Grid>

            <Grid item>
              <IconButton onClick={() => dispatch(deleteOrderDetail(index))}>
                <ClearIcon />
              </IconButton>
            </Grid>
          </Grid>
        </CardContent>
      </Card>
    </Grid>
  );
}

export default OrderDetails;
