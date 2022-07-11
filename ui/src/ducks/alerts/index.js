import { createAction, handleActions } from 'redux-actions'

const alertActions = {
  CLOSE_ALERT: 'ota/alert/CLOSE_ALERT',
  OPEN_ALERT: 'ota/alert/OPEN_ALERT',
  OPEN_SUCCESS: 'ota/alert/OPEN_SUCCESS',
  OPEN_WARN: 'ota/alert/OPEN_WARN',
  OPEN_ERROR: 'ota/alert/OPEN_ERROR',
  RESET_ALERT: 'ota/alert/RESET_ALERT',
}

export let defaultState = {
  error: false,
  message: '',
  open: false,
  success: false,
  warning: false,
}

export const closeAlert = createAction(alertActions.CLOSE_ALERT)

export const resetAlert = createAction(alertActions.RESET_ALERT)

export const openAlert = createAction(alertActions.OPEN_ALERT, (payload) =>
  (dispatch, getState, config) => payload)

export const openSuccess = createAction(alertActions.OPEN_SUCCESS, (payload) =>
  (dispatch, getState, config) => payload)

export const openError = createAction(alertActions.OPEN_ERROR, (payload) =>
  (dispatch, getState, config) => payload)

export const openWarning = createAction(alertActions.OPEN_WARN, (payload) =>
  (dispatch,getState,config) => payload)

export default handleActions({
  [alertActions.CLOSE_ALERT]: (state) => ({
    ...state,
    open: false,
  }),
  [alertActions.OPEN_ALERT]: (state, action) => ({
    error: false,
    message: action.payload,
    open: true,
    success: false,
    warning: false,
  }),
  [alertActions.OPEN_SUCCESS]: (state, action) => ({
    error: false,
    message: action.payload,
    open: true,
    success: true,
    warning: false,
  }),
  [alertActions.OPEN_ERROR]: (state, action) => ({
    error: true,
    message: action.payload,
    open: true,
    success: false,
    warning: false,
  }),
  [alertActions.OPEN_WARN]: (state, action) => ({
    error: false,
    message: action.payload,
    open: true,
    success: false,
    warning: true,
  }),
  [alertActions.RESET_ALERT]: (state) => ({
    error: false,
    message: '',
    open: false,
    success: false,
    warning: false,
  })
}, defaultState)