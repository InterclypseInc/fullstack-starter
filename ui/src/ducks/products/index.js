import axios from 'axios'
import { createAction, handleActions } from 'redux-actions'

const actions = {
  PRODUCTS_GET_ALL: 'products/get_all',
  PRODUCTS_GET_ALL_PENDING: 'products/get_all_PENDING',
  PRODUCTS_SAVE: 'products/save',
  PRODUCTS_DELETE: 'products/delete',
  PRODUCTS_REFRESH: 'products/refresh'
}

export let defaultState = {
  all: [],
  fetched: false,
}

export const findProducts = createAction(actions.PRODUCTS_GET_ALL, () =>
  (dispatch, getState, config) => axios
    .get(`${config.restAPIUrl}/products`)
    .then((suc) => dispatch(refreshProducts(suc.data)))
)

export const saveProducts = createAction(actions.PRODUCTS_SAVE, (product) =>
  (dispatch, getState, config) => axios
    .post(`${config.restAPIUrl}/products`, product)
    .then((suc) => {
      const invs = []
      getState().products.all.forEach(inv => {
        if (inv.id !== suc.data.id) {
          invs.push(inv)
        }
      })
      invs.push(suc.data)
      dispatch(refreshProducts(invs))
    })
)

export const removeProducts = createAction(actions.PRODUCTS_DELETE, (ids) =>
  (dispatch, getState, config) => axios
    .delete(`${config.restAPIUrl}/products`, { data: ids })
    .then((suc) => {
      const invs = []
      getState().products.all.forEach(inv => {
        if (!ids.includes(inv.id)) {
          invs.push(inv)
        }
      })
      dispatch(refreshProducts(invs))
    })
)

export const refreshProducts = createAction(actions.PRODUCTS_REFRESH, (payload) =>
  (dispatcher, getState, config) =>
    payload.sort((productA, productB) => productA.name < productB.name ? -1 : productA.name > productB.name ? 1 : 0)
)

export default handleActions({
  [actions.PRODUCTS_GET_ALL_PENDING]: (state) => ({
    ...state,
    fetched: false
  }),
  [actions.PRODUCTS_REFRESH]: (state, action) => ({
    ...state,
    all: action.payload,
    fetched: true,
  })
}, defaultState)
