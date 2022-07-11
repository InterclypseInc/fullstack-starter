import alertDuck from '../ducks/alerts'
import { combineReducers } from 'redux'
import inventoryDuck from '../ducks/inventory'
import productsDuck from '../ducks/products'

export default combineReducers({
  alerts: alertDuck,
  config: (state = {}, action) => state,
  inventory: inventoryDuck,
  products: productsDuck
})