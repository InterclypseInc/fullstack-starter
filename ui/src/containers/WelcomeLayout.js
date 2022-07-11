import ArrowForwardIcon from '@material-ui/icons/PlayArrow'
import ArrowRightIcon from '@material-ui/icons/PlayArrowOutlined'
import Divider from '@material-ui/core/Divider'
import List from '@material-ui/core/List'
import ListItem from '@material-ui/core/ListItem'
import ListItemIcon from '@material-ui/core/ListItemIcon'
import { makeStyles } from '@material-ui/core/styles'
import React from 'react'
import Typography from '@material-ui/core/Typography'

const useStyles = makeStyles((theme) => ({
  gridItem: {
    padding: theme.spacing(2)
  },
  paddingTop: {
    padding: theme.spacing(1),
    paddingBottom: 0,
    paddingLeft: theme.spacing(2),
    paddingRight: theme.spacing(2),
  },
  paddingBottom: {
    padding: theme.spacing(1),
    paddingTop: 0,
    paddingLeft: theme.spacing(2),
    paddingRight: theme.spacing(2),
  },
  root: {
    maxHeight: '70vh',
    overflow: 'auto',
    padding: theme.spacing(2)
  },
  nested: {
    paddingLeft: theme.spacing(6),
  },
}))

const WelcomeLayout = () => {
  const classes = useStyles()
  return (
    <React.Fragment>
      <Typography variant='h4' className={classes.gridItem}>
        Welcome to the Exerceo Starter Application.
      </Typography>
      <Divider/>
      <Typography variant='subtitle1' className={classes.paddingTop}>
        In order to be successful in the Javascript world, we have adopted several best practices and picked
        specific libraries based on our lessons learned.
      </Typography>
      <Typography variant='subtitle1' className={classes.paddingBottom}>
        The following is not an exhaustive list, but it covers
        the vast majority of the libraries we use.
      </Typography>
      <Divider/>
      <List component='div' disablePadding className={classes.root}>
        <ListItem
          button
          component='a'
          href='https://github.com/erikras/ducks-modular-redux'
          rel='noopener noreferrer'
          target='_blank'>
          <ListItemIcon><ArrowForwardIcon /></ListItemIcon>
          <Typography>Ducks Architecture (Redux implementation best practice)</Typography>
        </ListItem>
        <ListItem
          button
          component='a'
          href='https://www.youtube.com/playlist?list=PLoYCgNOIyGACDQLaThEEKBAlgs4OIUGif'
          rel='noopener noreferrer'
          target='_blank'>
          <ListItemIcon><ArrowForwardIcon/></ListItemIcon>
          <Typography>EcmaScript 2016 (ES6) Tutorials</Typography>
        </ListItem>
        <ListItem
          button
          component='a'
          href='https://github.com/acdlite/flux-standard-action'
          rel='noopener noreferrer'
          target='_blank'>
          <ListItemIcon><ArrowForwardIcon /></ListItemIcon>
          <Typography>Flux Standard Actions</Typography>
        </ListItem>
        <ListItem>
          <ListItemIcon><ArrowForwardIcon /></ListItemIcon>
          <Typography>React and Redux (Flux)</Typography>
        </ListItem>
        <List component='div' disablePadding>
          <ListItem
            button
            component='a'
            href='https://facebook.github.io/flux/docs/in-depth-overview'
            rel='noopener noreferrer'
            target='_blank'
            className={classes.nested}>
            <ListItemIcon><ArrowRightIcon/></ListItemIcon>
            <Typography>
              Flux Architecture
            </Typography>
          </ListItem>
          <ListItem
            button
            component='a'
            href='https://reactjs.org/docs/getting-started.html'
            rel='noopener noreferrer'
            target='_blank'
            className={classes.nested}>
            <ListItemIcon><ArrowRightIcon/></ListItemIcon>
            <Typography>
              React Starter Documentation
            </Typography>
          </ListItem>
          <ListItem
            button
            component='a'
            href='https://www.youtube.com/playlist?list=PLoYCgNOIyGABj2GQSlDRjgvXtqfDxKm5b'
            rel='noopener noreferrer'
            target='_blank'
            className={classes.nested}>
            <ListItemIcon><ArrowRightIcon/></ListItemIcon>
            <Typography>
              React/Redux Tutorials
            </Typography>
          </ListItem>
          <ListItem
            button
            component='a'
            href='https://redux.js.org/introduction/getting-started'
            rel='noopener noreferrer'
            target='_blank'
            className={classes.nested}>
            <ListItemIcon><ArrowRightIcon/></ListItemIcon>
            <Typography>
              Redux Documentation
            </Typography>
          </ListItem>
          <ListItem
            button
            component='a'
            href='https://material-ui.com/'
            rel='noopener noreferrer'
            target='_blank'
            className={classes.nested}>
            <ListItemIcon><ArrowRightIcon/></ListItemIcon>
            <Typography>
              Material-UI
            </Typography>
          </ListItem>
        </List>
        <ListItem
          button
          component='a'
          href='https://redux.js.org/introduction/ecosystem'
          rel='noopener noreferrer'
          target='_blank'>
          <ListItemIcon><ArrowForwardIcon /></ListItemIcon>
          <Typography>Redux Ecosystem</Typography>
        </ListItem>
        <List component='div' disablePadding>
          <ListItem
            button
            component='a'
            href='https://github.com/reduxjs/react-redux'
            rel='noopener noreferrer'
            target='_blank'
            className={classes.nested}>
            <ListItemIcon><ArrowRightIcon/></ListItemIcon>
            <Typography>
              React-Redux
            </Typography>
          </ListItem>
          <ListItem
            button
            component='a'
            href='https://github.com/reduxactions/redux-actions'
            rel='noopener noreferrer'
            target='_blank'
            className={classes.nested}>
            <ListItemIcon><ArrowRightIcon/></ListItemIcon>
            <Typography>
              Redux actions
            </Typography>
          </ListItem>
          <ListItem
            button
            component='a'
            href='https://github.com/reduxjs/reselect'
            rel='noopener noreferrer'
            target='_blank'
            className={classes.nested}>
            <ListItemIcon><ArrowRightIcon/></ListItemIcon>
            <Typography>
              Reselect
            </Typography>
          </ListItem>
          <ListItem
            button
            component='a'
            href='https://github.com/gaearon/redux-thunk'
            rel='noopener noreferrer'
            target='_blank'
            className={classes.nested}>
            <ListItemIcon><ArrowRightIcon/></ListItemIcon>
            <Typography>
              Thunk (Note: We have constructed our own thunk middleware utilizing Flux Standard Actions.)
            </Typography>
          </ListItem>
          <ListItem
            button
            component='a'
            href='https://github.com/pburtchaell/redux-promise-middleware'
            rel='noopener noreferrer'
            target='_blank'
            className={classes.nested}>
            <ListItemIcon><ArrowRightIcon/></ListItemIcon>
            <Typography>
              Redux Promise Middleware
            </Typography>
          </ListItem>
          <ListItem
            button
            component='a'
            href='https://github.com/evgenyrodionov/redux-logger'
            rel='noopener noreferrer'
            target='_blank'
            className={classes.nested}>
            <ListItemIcon><ArrowRightIcon/></ListItemIcon>
            <Typography>
              Redux Logger
            </Typography>
          </ListItem>
          <ListItem
            button
            component='a'
            href='https://reacttraining.com/react-router/'
            rel='noopener noreferrer'
            target='_blank'
            className={classes.nested}>
            <ListItemIcon><ArrowRightIcon/></ListItemIcon>
            <Typography>
              React Router
            </Typography>
          </ListItem>
          <ListItem
            button
            component='a'
            href='https://formik.org/docs/overview'
            rel='noopener noreferrer'
            target='_blank'
            className={classes.nested}>
            <ListItemIcon><ArrowRightIcon/></ListItemIcon>
            <Typography>
              Formik
            </Typography>
          </ListItem>
        </List>

        <ListItem
          button
          component='a'
          href='https://medium.com/@dan_abramov/smart-and-dumb-components-7ca2f9a7c7d0'
          rel='noopener noreferrer'
          target='_blank'>
          <ListItemIcon><ArrowForwardIcon /></ListItemIcon>
          <Typography>Smart Containers vs Dumb Components</Typography>
        </ListItem>

      </List>
    </React.Fragment>
  )
}

export default WelcomeLayout
