import React, { useEffect, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import Paper from '@material-ui/core/Paper';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableBody from '@material-ui/core/TableBody';
import Table from '@material-ui/core/Table';
import TableCell from '@material-ui/core/TableCell';
import TableRow from '@material-ui/core/TableRow';
import { formatData, formatTitle, getAlignment } from '../utils/util';
import { makeStyles } from '@material-ui/core/styles';
import IconButton from '@material-ui/core/IconButton';
import AddIcon from '@material-ui/icons/Add';
import EditIcon from '@material-ui/icons/Edit';
import DeleteIcon from '@material-ui/icons/Delete';
import Popup from '../components/Popup';
import Grid from '@material-ui/core/Grid';
import Fab from '@material-ui/core/Fab';
import { apiUrl, get } from '../utils/network';
import { useDispatch } from 'react-redux';
import { setNavbarTitle } from '../store/actions/navigation';

const useStyles = makeStyles({
  container: {
    height: '75vh',
  },
  header: {
    backgroundColor: 'lightgray',
    textTransform: 'capitalize',
    fontWeight: 'bold',
  },
  actions: {
    width: 100,
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

function DBTable() {
  const classes = useStyles();
  const dispatch = useDispatch();
  const history = useHistory();
  const { table } = useParams();

  const [columns, setColumns] = useState([]);
  const [rows, setRows] = useState([]);
  const [openDialog, setOpenDialog] = useState(false);
  const [selectedId, setSelectedId] = useState(0);

  function redirect(id) {
    if (id) {
      dispatch(setNavbarTitle(`Edit ${id} from ${table}`));
      history.push(`/${table}/edit/${id}`);
    } else {
      dispatch(setNavbarTitle(`Add new entry to ${table}`));
      history.push(`/${table}/add`);
    }
  }

  async function handleDelete() {
    const response = await fetch(apiUrl + `/${table}/${selectedId}`, { method: 'DELETE' });
    if (response.ok) setRows(rows.filter(item => item.id !== selectedId));
      else alert(await response.text());
  }

  useEffect(() => {
    dispatch(setNavbarTitle('All ' + table));

    fetch(apiUrl + `/${table}/column_names`)
      .then((response) => response.json())
      .then((data) => {
        let arr = data.map((name) => Object.values(name)[0]);
        arr.push('Actions');
        setColumns(arr);
      })
      .catch((error) => console.log(error));

    get(`/${table}`, setRows);
  }, [table, dispatch]);

  return (
    <>
      <Popup open={openDialog} onClose={() => setOpenDialog(false)} onDelete={handleDelete} />
      <Fab color="primary" onClick={() => redirect()} className={classes.fab}>
        <AddIcon />
      </Fab>

      <Grid container direction={'column'} alignItems={'center'}>
        <Paper elevation={4}>
          <TableContainer className={classes.container}>
            <Table stickyHeader>
              <TableHead>
                <TableRow>
                  {columns.map((name, index) => (
                    <TableCell align={'center'} className={classes.header} key={index}>
                      {formatTitle(name)}
                    </TableCell>
                  ))}
                </TableRow>
              </TableHead>

              <TableBody>
                {rows.map((row, index) => (
                  <TableRow key={index}>
                    {Object.values(row).map((item, index) => (
                      <TableCell align={getAlignment(columns[index])} key={index}>
                        {formatData(columns[index], item)}
                      </TableCell>
                    ))}

                    <TableCell align={'center'} className={classes.actions}>
                      <IconButton onClick={() => redirect(row.id)}>
                        <EditIcon />
                      </IconButton>

                      <IconButton
                        onClick={() => {
                          setSelectedId(row.id);
                          setOpenDialog(true);
                        }}
                      >
                        <DeleteIcon />
                      </IconButton>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </Paper>
      </Grid>
    </>
  );
}

export default DBTable;
