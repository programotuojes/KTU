import React, { useEffect } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import { makeStyles } from '@material-ui/core/styles';
import Fab from '@material-ui/core/Fab';
import SaveIcon from '@material-ui/icons/Save';
import { useDispatch, useSelector } from 'react-redux';
import Designer from '../components/Designer';
import { fetchDesigner, resetDesigner } from '../store/actions/designer';
import { addOneDesigner, updateOneDesigner } from '../utils/network';
import { getDesigner } from '../store/selectors/designer';

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

function EditOneDesigner() {
  const classes = useStyles();
  const dispatch = useDispatch();
  const history = useHistory();
  const { id } = useParams();

  const designer = useSelector(getDesigner);

  async function submit() {
    const response = id ? await updateOneDesigner(designer) : await addOneDesigner(designer);

    if (response.ok) history.push('/designers');
    else alert(await response.text());
  }

  useEffect(() => {
    if (id) dispatch(fetchDesigner(id));
    else dispatch(resetDesigner());
  }, [id, dispatch]);

  return (
    <>
      <Fab onClick={submit} className={classes.fab}>
        <SaveIcon />
      </Fab>

      <Designer />
    </>
  );
}

export default EditOneDesigner;
