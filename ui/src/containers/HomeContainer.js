import AppBar from '@material-ui/core/AppBar'
import ArrowLeft from '@material-ui/icons/FirstPage'
import ArrowRight from '@material-ui/icons/LastPage'
import CategoryIcon from '@material-ui/icons/CategoryOutlined'
import classNames from 'classnames'
import DarkIcon from '@material-ui/icons/Brightness4'
import Divider from '@material-ui/core/Divider'
import Drawer from '@material-ui/core/Drawer'
import HomeIcon from '@material-ui/icons/Home'
import IconButton from '@material-ui/core/IconButton'
import InventoryLayout from './InventoryLayout'
import LightIcon from '@material-ui/icons/Brightness5'
import List from '@material-ui/core/List'
import ListIcon from '@material-ui/icons/List'
import ListItem from '@material-ui/core/ListItem'
import ListItemIcon from '@material-ui/core/ListItemIcon'
import ListItemText from '@material-ui/core/ListItemText'
import { makeStyles } from '@material-ui/core/styles'
import ProductLayout from './ProductLayout'
import PropTypes from 'prop-types'
import React from 'react'
import Toolbar from '@material-ui/core/Toolbar'
import Tooltip from '@material-ui/core/Tooltip'
import Typography from '@material-ui/core/Typography'
import WelcomeLayout from './WelcomeLayout'
import { NavLink, Redirect } from 'react-router-dom'
import { Route, Switch } from 'react-router'

const drawerWidth = 220

const useStyles = makeStyles((theme) => ({
  root: {
    display: 'flex',
  },
  appBar: {
    zIndex: theme.zIndex.drawer + 1,
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
  },
  content: {
    flexGrow: 1,
    padding: theme.spacing(3),
  },
  drawer: {
    width: drawerWidth,
    flexShrink: 0,
    whiteSpace: 'nowrap',
  },
  drawerOpen: {
    width: drawerWidth,
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
    overflowX: 'hidden',
  },
  drawerClose: {
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    overflowX: 'hidden',
    width: theme.spacing(7) + 1,
  },
  flex: {
    flex: 1
  },
  listIcon: {
    minWidth: 40
  },
  logo: {
    content: theme.logo,
    width: 150,
    marginRight: 24,
  },
  iconWrapper: {
    display: 'flex',
    justifyContent: 'flex-end'
  }
}))

const configureTabs = (props) => [
  { name: 'Home', icon: <HomeIcon/>, path: props.publicPath, component: WelcomeLayout, divide: true, exact: true },
  { name: 'Inventory', icon: <ListIcon/>, path: '/inventory', component: InventoryLayout, divide: false, exact: false },
  { name: 'Products', icon: <CategoryIcon/>, path: '/products', component: ProductLayout, divide: false, exact: false }
]

const HomeContainer = (props) => {
  const classes = useStyles()
  const [open, setOpen] = React.useState(false)
  const tabs = configureTabs(props)

  const handleDrawerToggle = () => {
    setOpen(!open)
  }

  return (
    <div className={classes.root}>
      <AppBar position="fixed" className={classes.appBar} color='secondary'>
        <Toolbar variant='dense'>
          <div className={classes.logo}/>
          <Typography variant="h6" noWrap>
            Exerceo Starter Application
          </Typography>
          <div className={classes.flex}/>
          <Tooltip title='Toggle Theme' arrow placement='bottom'>
            <IconButton size='small' onClick={props.toggleTheme}>
              {props.theme === 'dark' ? <LightIcon/> : <DarkIcon/>}
            </IconButton>
          </Tooltip>
        </Toolbar>
      </AppBar>
      <Drawer
        className={classNames(classes.drawer, {
          [classes.drawerOpen]: open,
          [classes.drawerClose]: !open
        })}
        variant='permanent'
        classes={{
          paper: classNames({
            [classes.drawerOpen]: open,
            [classes.drawerClose]: !open
          }),
        }}
      >
        <Toolbar variant='dense'/>
        <div className={classes.drawerContainer}>
          <List disablePadding>
            {tabs.map((tab, index) =>
              <React.Fragment key={index}>
                <Tooltip title={tab.name} arrow placement='right'>
                  <ListItem button key={index} dense component={NavLink} to={tab.path}>
                    <ListItemIcon className={classes.listIcon}>
                      {tab.icon}
                    </ListItemIcon>
                    <ListItemText primary={tab.name}/>
                  </ListItem>
                </Tooltip>
                {tab.divide ? <Divider/> : null}
              </React.Fragment>
            )}
          </List>
          <Divider/>
        </div>
        <div className={classes.flex}/>
        <div className={classes.iconWrapper}>
          <IconButton
            onClick={handleDrawerToggle}
          >
            { open ? <ArrowLeft/> : <ArrowRight/> }
          </IconButton>
        </div>
      </Drawer>
      <main className={classes.content}>
        <Toolbar variant='dense'/>
        <Switch>
          {tabs.map((tab, index) =>
            <Route key={index} exact={tab.exact} path={tab.path} component={tab.component}/>
          )}
          <Redirect to={props.publicPath}/>
        </Switch>
      </main>
    </div>
  )
}

HomeContainer.propTypes = {
  window: PropTypes.func,
}

export default HomeContainer
