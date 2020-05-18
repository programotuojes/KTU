import React, { useEffect, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import { makeStyles } from '@material-ui/core/styles';
import Fab from '@material-ui/core/Fab';
import SaveIcon from '@material-ui/icons/Save';
import { useDispatch, useSelector } from 'react-redux';
import { addClothes, get, updateClothes } from '../utils/network';
import * as paths from '../utils/paths';
import Typography from '@material-ui/core/Typography';
import { getDesignerClothingById } from '../store/selectors/designerClothes';
import Clothes from '../components/Clothes';
import { fetchClothes, resetDesignerClothing } from '../store/actions/designerClothes';

const useStyles = makeStyles({
  title: {
    marginTop: 30,
    marginBottom: 10,
    fontSize: '1.5em',
  },
  fab: {
    margin: 0,
    top: 'auto',
    right: 20,
    bottom: 20,
    left: 'auto',
    position: 'fixed',
  },
});

function EditClothes() {
  const classes = useStyles();
  const dispatch = useDispatch();
  const history = useHistory();
  const { id } = useParams();

  const clothes = useSelector(getDesignerClothingById(0));

  const [styles, setStyles] = useState([]);
  const [providers, setProviders] = useState([]);
  const [designers, setDesigners] = useState([]);

  async function submit() {
    const response = id ? await updateClothes(clothes, id) : await addClothes(clothes);
    if (response.ok) history.push('/clothes');
    else alert(await response.text());
  }

  useEffect(() => {
    get(paths.STYLE_NAMES, setStyles);
    get(paths.PROVIDER_NAMES, setProviders);
    get(paths.DESIGNER_NAMES, setDesigners);

    if (id) dispatch(fetchClothes(id));
    else dispatch(resetDesignerClothing());
  }, [id, dispatch]);

  return (
    <>
      <Fab onClick={submit} className={classes.fab}>
        <SaveIcon />
      </Fab>

      <Typography align={'center'} className={classes.title}>
        Clothing details
      </Typography>
      <Clothes designers={designers} styles={styles} providers={providers} />
    </>
  );
}

export default EditClothes;
