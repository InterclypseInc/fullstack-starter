import { isFSA } from 'flux-standard-action'

/**
 * FSA compliant middlware which handles if the payload of an action is a function.
 */
const thunkMiddleware = (extraArg) =>
  ({ dispatch, getState }) =>
    (next)=>
      (action) => {
        if (isFSA(action) && typeof action.payload === 'function') {
          return next(
            {
              ...action,
              payload: action.payload(dispatch, getState, extraArg)
            }
          )
        } else {
          return next(action)
        }
      }
export { thunkMiddleware }