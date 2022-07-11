import axios from 'axios'
import { createLogger } from 'redux-logger'
import DesignLayout from './containers/DesignLayout'
import HomeContainer from './containers/HomeContainer'
import { Provider } from 'react-redux'
import React from 'react'
import ReactDom from 'react-dom'
import reducers from './reducers'
import { BrowserRouter as Router } from 'react-router-dom'
import { thunkMiddleware } from './middleware'
import { applyMiddleware, createStore } from 'redux'
import 'core-js/es/map'
import 'core-js/es/set'

axios.defaults.withCredentials = true

const configPath = '/config.json'
const defaultConfig = {
  restAPIUrl: 'http://localhost:8080',
  publicPath: '/',
}

axios.get(`${configPath}`).then(
  (success) => {
    renderApplication(Object.assign(defaultConfig, success.data))
  },
  (failure) => {
    renderApplication(defaultConfig)
  }
)

const renderApplication = (overrideConfig) => {
  const config = {
    ...overrideConfig
  }

  let middleware = [thunkMiddleware(config)]

  if (process.env.NODE_ENV !== 'production') {
    middleware = [...middleware, createLogger()]
  }

  const store = createStore(reducers, { config: config }, applyMiddleware(...middleware))

  ReactDom.render(
    <Provider store={store}>
      <Router>
        <DesignLayout>
          <HomeContainer publicPath={config.publicPath}/>
        </DesignLayout>
      </Router>
    </Provider>,
    document.getElementById('app')
  )
}