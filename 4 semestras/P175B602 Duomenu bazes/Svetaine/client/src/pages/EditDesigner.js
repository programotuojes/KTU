import React, { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Fab from '@material-ui/core/Fab';
import AddIcon from '@material-ui/icons/Add';
import SaveIcon from '@material-ui/icons/Save';
import { useDispatch, useSelector } from 'react-redux';
import Designer from '../components/Designer';
import { resetDesigner } from '../store/actions/designer';
import { getDesignerClothes } from '../store/selectors/designerClothes';
import DesignerClothes from '../components/DesignerClothes';
import { addDesigner, get } from '../utils/network';
import * as paths from '../utils/paths';
import {
  addDesignerClothing,
  resetDesignerClothing
} from '../store/actions/designerClothes';
import { getDesigner } from '../store/selectors/designer';

const useStyles = makeStyles({
  title: {
    marginTop: 30,
    marginBottom: 10,
    fontSize: '1.5em',
  },
  fabAdd: {
    margin: 0,
    top: 'auto',
    right: 20,
    bottom: 20,
    left: 'auto',
    position: 'fixed',
  },
  fabSave: {
    margin: 0,
    top: 'auto',
    right: 20,
    bottom: 100,
    left: 'auto',
    position: 'fixed',
  },
});

function EditDesigner() {
  const classes = useStyles();
  const dispatch = useDispatch();
  const history = useHistory();

  const designer = useSelector(getDesigner);
  const designerClothes = useSelector(getDesignerClothes);

  const [styles, setStyles] = useState([]);
  const [providers, setProviders] = useState([]);

  async function submit() {
    const response = await addDesigner(designer, designerClothes);
    if (response.ok) history.push('/designers');
    else alert(await response.text());
  }

  useEffect(() => {
    dispatch(resetDesigner());
    dispatch(resetDesignerClothing());

    get(paths.STYLE_NAMES, setStyles);
    get(paths.PROVIDER_NAMES, setProviders);
  }, [dispatch]);

  return (
    <>
      <Fab
        color={'primary'}
        onClick={() => dispatch(addDesignerClothing())}
        className={classes.fabAdd}
      >
        <AddIcon />
      </Fab>
      <Fab onClick={submit} className={classes.fabSave}>
        <SaveIcon />
      </Fab>

      <Grid container direction={'column'} justify={'center'}>
        <Designer />

        <Typography align={'center'} className={classes.title}>
          Clothes
        </Typography>
        {designerClothes.map((item, index) => (
          <DesignerClothes styles={styles} providers={providers} index={index} key={index} />
        ))}
      </Grid>
    </>
  );
}

export default EditDesigner;
