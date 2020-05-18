import { createSelector } from 'reselect';

export const getOrder = createSelector(
  (state) => state.order,
  (order) => order
);
