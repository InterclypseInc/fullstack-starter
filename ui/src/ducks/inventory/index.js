import { createAction, handleActions } from 'redux-actions'

const actions = {
  INVENTORY_GET_ALL: 'inventory/get_all',
}

export let defaultState = {
  all: []
}

export const findInventory = createAction(actions.INVENTORY_GET_ALL, () => {
  //TODO
})

export default handleActions({
  //TODO
}, defaultState)
