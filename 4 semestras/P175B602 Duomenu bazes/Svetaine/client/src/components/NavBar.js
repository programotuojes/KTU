import React from 'react';
import { useHistory } from 'react-router-dom';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import Drawer from '@material-ui/core/Drawer';
import makeStyles from '@material-ui/core/styles/makeStyles';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import ListSubheader from '@material-ui/core/ListSubheader';
import Typography from '@material-ui/core/Typography';
import Divider from '@material-ui/core/Divider';
import { useDispatch, useSelector } from 'react-redux';
import { getNavbarTitle, isSidebarOpen } from '../store/selectors/navigation';
import { setNavbarTitle, toggleSidebar } from '../store/actions/navigation';

const useStyles = makeStyles({
  appBar: {
    zIndex: 3000,
  },
  sidebar: {
    width: 250,
  },
  title: {
    marginLeft: 8,
  },
});

function NavBar() {
  const classes = useStyles();
  const dispatch = useDispatch();
  const history = useHistory();

  const open = useSelector(isSidebarOpen);
  const title = useSelector(getNavbarTitle);

  function redirect(path, title) {
    dispatch(toggleSidebar());
    dispatch(setNavbarTitle(title));
    history.push(path);
  }

  return (
    <>
      <AppBar className={classes.appBar}>
        <Toolbar>
          <IconButton onClick={() => dispatch(toggleSidebar())} edge="start">
            <MenuIcon style={{ color: 'white' }} />
          </IconButton>
          <Typography className={classes.title}>{title}</Typography>
        </Toolbar>
      </AppBar>

      <Toolbar />

      <Drawer open={open} onClose={() => dispatch(toggleSidebar())}>
        <Toolbar />

        <List className={classes.sidebar}>
          <ListSubheader>All items</ListSubheader>

          <ListItem button onClick={() => redirect('/designers', 'All designers')}>
            <ListItemText primary={'Designers'} />
          </ListItem>
          <ListItem button onClick={() => redirect('/clothes', 'All clothes')}>
            <ListItemText primary={'Clothes'} />
          </ListItem>
          <ListItem button onClick={() => redirect('/employees', 'All employees')}>
            <ListItemText primary={'Employees'} />
          </ListItem>
          <ListItem button onClick={() => redirect('/orders', 'All orders')}>
            <ListItemText primary={'Orders'} />
          </ListItem>

          <Divider />

          <ListSubheader>Add</ListSubheader>

          <ListItem button onClick={() => redirect('/designers/add', 'Add new entry to designers')}>
            <ListItemText primary={'Designer (PN1)'} />
          </ListItem>
          <ListItem button onClick={() => redirect('/employees/add', 'Add new entry to employees')}>
            <ListItemText primary={'Employee (PP1)'} />
          </ListItem>
          <ListItem button onClick={() => redirect('/designer/add', 'Add new designer with clothes')}>
            <ListItemText primary={'Designer with clothes (SPa)'} />
          </ListItem>
          <ListItem button onClick={() => redirect('/orders/add', 'Add new entry to orders')}>
            <ListItemText primary={'Order with details (SPva)'} />
          </ListItem>
        </List>
      </Drawer>
    </>
  );
}

export default NavBar;
