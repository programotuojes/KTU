import { SET_NAVBAR_TITLE, TOGGLE_SIDEBAR } from '../actionTypes';

export function setNavbarTitle(title) {
  return {
    type: SET_NAVBAR_TITLE,
    title,
  };
}

export function toggleSidebar() {
  return {
    type: TOGGLE_SIDEBAR,
  };
}
