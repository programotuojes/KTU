import { createSelector } from 'reselect';

export const getDesigner = createSelector(
  (state) => state.designer,
  (designer) => designer
);
