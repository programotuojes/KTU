import React, { useEffect, useState } from 'react';
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
import { updateOrder } from '../store/actions/order';
import { STATUS } from '../utils/constants';
import * as paths from '../utils/paths';
import { get } from '../utils/network';
import { getOrder } from '../store/selectors/order';
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

function Order() {
  const classes = useStyles();
  const dispatch = useDispatch();

  const order = useSelector(getOrder);

  const [shops, setShops] = useState([]);
  const [customers, setCustomers] = useState([]);

  useEffect(() => {
    get(paths.SHOP_NAMES, setShops);
    get(paths.CUSTOMER_NAMES, setCustomers);
  }, []);

  return (
    <>
      <Typography align={'center'} className={classes.title}>
        Order information
      </Typography>
      <Grid container justify={'center'}>
        <Card elevation={4}>
          <CardContent>
            <Grid container direction={'column'} alignItems={'center'}>
              <Grid item>
                <FormControl required className={classes.form}>
                  <InputLabel id={'customer-label'}>Customer</InputLabel>
                  <Select
                    value={order.customer_id}
                    onChange={(event) => dispatch(updateOrder('customer_id', event.target.value))}
                    labelId={'customer-label'}
                  >
                    {customers.map((item, index) => (
                      <MenuItem
                        value={item.id}
                        key={index}
                      >{`${item.name} ${item.surname}`}</MenuItem>
                    ))}
                  </Select>
                </FormControl>

                <TextField
                  required
                  type={'text'}
                  label={'Ship to'}
                  value={order.ship_to}
                  onChange={(event) => dispatch(updateOrder('ship_to', event.target.value))}
                  className={classes.form}
                />

                <TextField
                  type={'text'}
                  label={'Comments'}
                  value={order.comments}
                  onChange={(event) => dispatch(updateOrder('comments', event.target.value))}
                  className={classes.form}
                />
              </Grid>

              <Grid item>
                <TextField
                  required
                  type={'datetime-local'}
                  InputLabelProps={{ shrink: true }}
                  label={'Date ordered'}
                  value={order.date_ordered}
                  onChange={(event) => dispatch(updateOrder('date_ordered', event.target.value))}
                  className={classes.form}
                />

                <TextField
                  type={'datetime-local'}
                  InputLabelProps={{ shrink: true }}
                  label={'Date shipped'}
                  value={order.date_shipped}
                  onChange={(event) => dispatch(updateOrder('date_shipped', event.target.value))}
                  className={classes.form}
                />

                <FormControl required className={classes.form}>
                  <InputLabel id={'status-label'}>Status</InputLabel>
                  <Select
                    value={order.status}
                    onChange={(event) => dispatch(updateOrder('status', event.target.value))}
                    labelId={'status-label'}
                  >
                    {STATUS.map((item, index) => (
                      <MenuItem value={item} key={index}>
                        {item}
                      </MenuItem>
                    ))}
                  </Select>
                </FormControl>
              </Grid>

              <Grid item>
                <FormControl className={classes.form}>
                  <InputLabel id={'shop-label'}>Shop</InputLabel>
                  <Select
                    value={order.shop_id}
                    onChange={(event) => dispatch(updateOrder('shop_id', event.target.value))}
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

export default Order;
