import { createSelector } from 'reselect';

export const isSidebarOpen = createSelector(
  (state) => state.navigation.isSidebarOpen,
  (isSidebarOpen) => isSidebarOpen
);

export const getNavbarTitle = createSelector(
  (state) => state.navigation.title,
  (title) => title
);
