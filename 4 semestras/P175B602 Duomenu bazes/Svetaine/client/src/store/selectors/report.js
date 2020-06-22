import { createSelector } from 'reselect';

export const getFilters = createSelector(
  (state) => state.report.filters,
  (filters) => filters
);

export const getErrors = createSelector(
  (state) => state.report.errors,
  (errors) => errors
);

export const getResults = createSelector(
    (state) => state.report.results,
    (results) => results
);


