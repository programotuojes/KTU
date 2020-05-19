import { createSelector } from 'reselect';

export const getOrderDetails = createSelector(
  (state) => state.orderDetails.details,
  (details) => details
);

export const getOrderDetailsById = (index) => {
  return createSelector(
    (state) => state.orderDetails.details,
    (details) => details[index]
  );
};

export const getUsedClothesIds = createSelector(
  (state) => state.orderDetails.details,
  (details) => {
    let arr = [];
    details.forEach((item) => arr.push(item.clothes_id));
    return arr;
  }
);
