import React, { useEffect } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import { makeStyles } from '@material-ui/core/styles';
import Fab from '@material-ui/core/Fab';
import SaveIcon from '@material-ui/icons/Save';
import { useDispatch, useSelector } from 'react-redux';
import { addEmployee, updateEmployee } from '../utils/network';
import { getEmployee } from '../store/selectors/employee';
import { fetchEmployee, resetEmployee } from '../store/actions/employee';
import Employee from '../components/Employee';

const useStyles = makeStyles({
  fab: {
    margin: 0,
    top: 'auto',
    right: 20,
    bottom: 20,
    left: 'auto',
    position: 'fixed',
  },
});

function EditEmployee() {
  const classes = useStyles();
  const dispatch = useDispatch();
  const history = useHistory();
  const { id } = useParams();

  const employee = useSelector(getEmployee);

  async function submit() {
    const response = id ? await updateEmployee(employee) : await addEmployee(employee);
    if (response.ok) history.push('/employees');
    else alert(await response.text());
  }

  useEffect(() => {
    if (id) dispatch(fetchEmployee(id));
    else dispatch(resetEmployee());
  }, [id, dispatch]);

  return (
    <>
      <Fab onClick={submit} className={classes.fab}>
        <SaveIcon />
      </Fab>

      <Employee />
    </>
  );
}

export default EditEmployee;
