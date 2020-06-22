import React from 'react';
import { BrowserRouter, Route, Switch, useParams } from 'react-router-dom';
import DBTable from './pages/DBTable';
import NavBar from './components/NavBar';
import EditOrder from './pages/EditOrder';
import EditDesigner from './pages/EditDesigner';
import EditOneDesigner from './pages/EditOneDesigner';
import EditEmployee from './pages/EditEmployee';
import EditClothes from './pages/EditClothes';
import Report from "./pages/Report";

function App() {
  return (
    <BrowserRouter>
      <NavBar />

      <Switch>
        <Route exact path={'/report'} component={Report} />
        <Route exact path={'/:table'} component={DBTable} />
        <Route path={'/:table/add'} component={RouteSwitch} />
        <Route exact path={'/:table/edit/:id'} component={RouteSwitch} />
      </Switch>
    </BrowserRouter>
  );
}

function RouteSwitch() {
  const { table } = useParams();

  switch (table) {
    case 'designers':
      return <EditOneDesigner />;
    case 'designer':
      return <EditDesigner />;
    case 'employees':
      return <EditEmployee />;
    case 'orders':
      return <EditOrder />;
    case 'clothes':
      return <EditClothes />;
    default:
      return <h1>whoops, forgot to do this i guess</h1>;
  }
}

export default App;
