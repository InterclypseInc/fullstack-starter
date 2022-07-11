import * as alertDuck from '../ducks/alerts'
import CloseIcon from '@material-ui/icons/Close'
import CssBaseline from '@material-ui/core/CssBaseline'
import IconButton from '@material-ui/core/IconButton'
import { makeStyles } from '@material-ui/core/styles'
import Snackbar from '@material-ui/core/Snackbar'
import { useDarkMode } from '../hooks'
import { withTheme } from '@material-ui/core/styles'
import { createMuiTheme, MuiThemeProvider } from '@material-ui/core/styles'
import { darkTheme, lightTheme } from '../themes'
import React, { Children } from 'react'
import { useDispatch, useSelector } from 'react-redux'

const useStyles = makeStyles((theme) => ({
  root: {
    height: '100vh',
    zIndex: 1,
    overflow: 'hidden',
    position: 'relative',
    display: 'flex',
  },
  content: {
    zIndex: 1,
    flexGrow: 1,
  },
  error: {
    //should probably pull from themes
    background: 'fireBrick',
  },
  generic: {
  },
  success: {
    //should probably pull from themes
    background: 'forestGreen',
  },
  warning: {
    //should probably pull from themes
    background: 'orange',
  },
}))

const getAlertStyle = (alerts, classes) =>
  alerts.error
    ? classes.error
    : alerts.warning
      ? classes.warning
      : alerts.success
        ? classes.success
        : classes.generic

const DesignLayout = (props) => {
  const classes = useStyles()
  const { children } = props

  const dispatch = useDispatch()
  const error = useSelector(state => state.alerts.error)
  const warning = useSelector(state => state.alerts.warning)
  const success = useSelector(state => state.alerts.success)
  const open = useSelector(state => state.alerts.open)
  const message = useSelector(state => state.alerts.message)

  const alerting = {
    error: error,
    warning: warning,
    success: success
  }

  const [theme, toggleTheme] = useDarkMode()
  const themeMode = theme === 'dark' ? darkTheme : lightTheme

  return (
    <div className={classes.root}>
      <Snackbar
        anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
        autoHideDuration={3000}
        message={message}
        onClose={() => { alertDuck.closeAlert() }}
        open={open}
        ContentProps={{
          id: 'toast-alert',
          className: getAlertStyle(alerting, classes)
        }}
        action={[
          <IconButton
            key='close'
            aria-label='Close'
            color='inherit'
            onClick={() => { dispatch(alertDuck.closeAlert()) }}
          >
            <CloseIcon/>
          </IconButton>,
        ]}
      />
      <MuiThemeProvider theme={createMuiTheme(themeMode)}>
        <CssBaseline/>
        <div id="main" className={classes.content}>
          { Children.map(children, child =>
            React.cloneElement(child, { ...child.props, theme: theme, toggleTheme: toggleTheme }))
          }
        </div>
      </MuiThemeProvider>
    </div>
  )
}

export default withTheme(DesignLayout)