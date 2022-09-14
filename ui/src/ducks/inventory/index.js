import axios from 'axios'
import { createAction, handleActions } from 'redux-actions'
import { openSuccess } from '../alerts';

const actions = {
  INVENTORIES_GET_ALL: 'inventories/get_all',
  INVENTORIES_PENDING: 'inventories/pending',
  INVENTORIES_SAVE: 'inventories/save',
  INVENTORIES_UPDATE: 'inventories/update',
  INVENTORIES_DELETE: 'inventories/delete',
  INVENTORIES_REFRESH: 'inventories/refresh'
}

export let defaultState = {
  all: []
}

export const findInventory = createAction(actions.INVENTORIES_GET_ALL, () =>
    (dispatch, getState, config) => axios
        .get(`${config.restAPIUrl}/inventories`)
        .then((suc) => dispatch(refreshInventories(suc.data)))
)

export const saveInventory = createAction(actions.INVENTORIES_SAVE, (inventory) =>
    (dispatch, getState, config) => axios
        .post(`${config.restAPIUrl}/inventories`, inventory)
        .then((suc) => {
          const invs = [...getState().inventory.all]
          invs.push(suc.data)
          dispatch(refreshInventories(invs))
          dispatch(openSuccess("Inventory Document Created"))
        })
)

export const updateInventory = createAction(actions.INVENTORIES_UPDATE, (id) =>
    (dispatch, getState, config) => axios
        .put(`${config.restAPIUrl}/inventories`, { data: id })
        .then((suc) => {
            const invs = []
            getState().inventory.all.forEach(inv => {
                if (!id.includes(inv.id)) {
                    invs.push(inv)
                }
            })
            invs.push(suc.data)
            dispatch(refreshInventories(invs))
            dispatch(openSuccess("Inventory Document Updated"))
        })
)

export const removeInventories = createAction(actions.INVENTORIES_DELETE, (ids) =>
    (dispatch, getState, config) => axios
        .delete(`${config.restAPIUrl}/inventories`, { data: ids })
        .then((suc) => {
            const invs = []
            getState().inventory.all.forEach(inv => {
                if (!ids.includes(inv.id)) {
                    invs.push(inv)
                }
            })
            dispatch(refreshInventories(invs))
            dispatch(openSuccess("Inventory Document Deleted"))
        })
)

export const refreshInventories = createAction(actions.INVENTORIES_REFRESH, (payload) =>
    (dispatcher, getState, config) =>
        payload.sort((inventoryA, inventoryB) => inventoryA.name < inventoryB.name ? -1 : inventoryA.name > inventoryB.name ? 1 : 0)
)

export default handleActions({
  [actions.INVENTORIES_PENDING]: (state) => ({
    ...state,
    fetched: false
  }),
  [actions.INVENTORIES_SAVE]: (state, action) => ({
    ...state,
    fetched: false
  }),
  [actions.INVENTORIES_UPDATE]: (state, action) => ({
    ...state,
    fetched: false
  }),
  [actions.INVENTORIES_DELETE]: (state, action) => ({
    ...state,
    fetched: false
  }),
  [actions.INVENTORIES_REFRESH]: (state, action) => ({
    ...state,
    all: action.payload,
    fetched: true,
  })
}, defaultState)

