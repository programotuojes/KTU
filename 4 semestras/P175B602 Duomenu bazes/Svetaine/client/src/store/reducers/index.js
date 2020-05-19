import { combineReducers } from 'redux';
import order from './order';
import orderDetails from './orderDetails';
import navigation from './navigation';
import designer from './designer';
import designerClothes from './designerClothes';
import employee from './employee';

export default combineReducers({
  order,
  orderDetails,
  navigation,
  designer,
  designerClothes,
  employee,
});
