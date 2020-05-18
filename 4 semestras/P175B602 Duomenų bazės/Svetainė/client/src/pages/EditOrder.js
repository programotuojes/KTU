import React, { useEffect, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import OrderDetails from '../components/OrderDetails';
import Grid from '@material-ui/core/Grid';
import Order from '../components/Order';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Fab from '@material-ui/core/Fab';
import AddIcon from '@material-ui/icons/Add';
import SaveIcon from '@material-ui/icons/Save';
import { useDispatch, useSelector } from 'react-redux';
import { getOrderDetails } from '../store/selectors/orderDetails';
import {
  addOrderDetail,
  fetchOrderDetails,
  resetOrderDetails,
} from '../store/actions/orderDetails';
import * as paths from '../utils/paths';
import { getOrder } from '../store/selectors/order';
import { addOrder, get, updateOrder } from '../utils/network';
import { fetchOrder, resetOrder } from '../store/actions/order';

const useStyles = makeStyles({
  title: {
    marginTop: 30,
    marginBottom: 10,
    fontSize: '1.5em',
  },
  fabAdd: {
    margin: 0,
    top: 'auto',
    right: 20,
    bottom: 20,
    left: 'auto',
    position: 'fixed',
  },
  fabSave: {
    margin: 0,
    top: 'auto',
    right: 20,
    bottom: 100,
    left: 'auto',
    position: 'fixed',
  },
});

function EditOrder() {
  const classes = useStyles();
  const dispatch = useDispatch();
  const history = useHistory();
  const { id } = useParams();

  const [clothes, setClothes] = useState([]);
  const order = useSelector(getOrder);
  const orderDetails = useSelector(getOrderDetails);

  async function submit() {
    const response = id
      ? await updateOrder(order, orderDetails)
      : await addOrder(order, orderDetails);

    if (response.ok) history.push('/orders');
    else alert(await response.text());
  }

  useEffect(() => {
    get(paths.CLOTHING_NAMES, setClothes);

    if (id) {
      dispatch(fetchOrder(id));
      dispatch(fetchOrderDetails(id));
    } else {
      dispatch(resetOrder());
      dispatch(resetOrderDetails());
    }
  }, [id, dispatch]);

  return (
    <>
      <Fab color={'primary'} onClick={() => dispatch(addOrderDetail())} className={classes.fabAdd}>
        <AddIcon />
      </Fab>
      <Fab disabled={orderDetails.length === 0} onClick={submit} className={classes.fabSave}>
        <SaveIcon />
      </Fab>

      <Grid container direction={'column'} justify={'center'}>
        <Order />

        <Typography align={'center'} className={classes.title}>
          Clothes
        </Typography>
        {orderDetails.map((item, index) => (
          <OrderDetails clothes={clothes} index={index} key={index} />
        ))}
      </Grid>
    </>
  );
}

export default EditOrder;
