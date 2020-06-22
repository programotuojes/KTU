import {
  RESET_REPORT,
  SET_REPORT_ERRORS,
  SHOW_REPORT_RESULTS,
  UPDATE_REPORT_FILTER,
} from '../actionTypes';
import { API_URL } from '../../utils/network';

export function updateFilter(key, value) {
  return {
    type: UPDATE_REPORT_FILTER,
    key,
    value,
  };
}

export function showResults(filters) {
  return (dispatch) => {
    fetch(`${API_URL}/report`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(filters),
    })
    .then((res) => {
      if (res.ok) return res.json();
      else throw res.text();
    })
    .then((results) => dispatch({ type: SHOW_REPORT_RESULTS, results }))
    .catch((error) => console.log(error));
  };
}

export function setErrors(errors) {
  return {
    type: SET_REPORT_ERRORS,
    errors,
  };
}

export function resetReport() {
  return {
    type: RESET_REPORT,
  };
}
