import { createSelector } from 'reselect';

export const getEmployee = createSelector(
  (state) => state.employee,
  (employee) => employee
);
