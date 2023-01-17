import axios from 'axios'
import { createAction, handleActions } from 'redux-actions'

const actions = {
  INVENTORY_GET_ALL: 'inventory/get_all',
  INVENTORY_REFRESH: 'inventory/refresh',

}

export let defaultState = {
  all: []
}

export const findInventory = createAction(actions.INVENTORY_GET_ALL, () => 
  (dispatch, getState, config) => axios
    .get(`${config.restAPIUrl}/inventory`)
    .then((suc) => dispatch(refreshInventory(suc.data)))
)

export const refreshInventory = createAction(actions.INVENTORY_REFRESH, (payload) =>
  (dispatcher, getState, config) =>
     payload.sort((inventoryA, inventoryB) => inventoryA.name < inventoryB.name ? -1 : productA.name > productB.name ? 1 : 0)
)

export default handleActions({
 [actions.INVENTORY_REFRESH]: (state, action) => ({
   ...state,
   all: action.payload,
   fetched: true,
  })
}, defaultState)
