import { SET_NAVBAR_TITLE, TOGGLE_SIDEBAR } from '../actionTypes';

const initialState = {
  isSidebarOpen: false,
  title: 'DB laboratorinis',
};

export default function navigationReducer(state = initialState, action) {
  switch (action.type) {
    case SET_NAVBAR_TITLE:
      return {
        ...state,
        title: action.title,
      };

    case TOGGLE_SIDEBAR:
      return {
        ...state,
        isSidebarOpen: !state.isSidebarOpen,
      };

    default:
      return {
        ...state,
      };
  }
}
