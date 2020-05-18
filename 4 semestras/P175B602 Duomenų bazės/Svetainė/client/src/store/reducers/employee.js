import { RESET_EMPLOYEE, SET_EMPLOYEE, UPDATE_EMPLOYEE } from '../actionTypes';
import { EMPLOYEE_TEMPLATE } from '../../utils/constants';

export default function employeeReducer(state = EMPLOYEE_TEMPLATE, action) {
  switch (action.type) {
    case SET_EMPLOYEE:
      return action.employee;

    case UPDATE_EMPLOYEE:
      return {
        ...state,
        [action.key]: action.value,
      };

    case RESET_EMPLOYEE:
      return EMPLOYEE_TEMPLATE;

    default:
      return state;
  }
}
