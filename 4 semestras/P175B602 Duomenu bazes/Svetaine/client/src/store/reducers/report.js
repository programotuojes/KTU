import {
  RESET_REPORT,
  SET_REPORT_ERRORS,
  SHOW_REPORT_RESULTS,
  UPDATE_REPORT_FILTER,
} from '../actionTypes';

const initialState = {
  results: undefined,
  filters: {
    dateFrom: '',
    dateTo: '',
    ageFrom: '',
    ageTo: '',
    status: '',
  },
  errors: {
    dateFrom: '',
    dateTo: '',
    ageFrom: '',
    ageTo: '',
    status: '',
  },
};

export default function orderReducer(state = initialState, action) {
  switch (action.type) {
    case UPDATE_REPORT_FILTER:
      return {
        ...state,
        filters: {
          ...state.filters,
          [action.key]: action.value,
        },
        errors: {
          ...state.errors,
          [action.key]: '',
        },
      };

    case SHOW_REPORT_RESULTS:
      return {
        ...state,
        results: action.results,
      };

    case SET_REPORT_ERRORS:
      return {
        ...state,
        errors: action.errors,
      };

    case RESET_REPORT:
      return initialState;

    default:
      return state;
  }
}
