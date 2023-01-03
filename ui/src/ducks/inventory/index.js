import { createAction, handleActions } from 'redux-actions';

const actions = {
	INVENTORY_GET_ALL: 'inventory/get_all',
};

export let defaultState = {
	all: [],
};

export const findInventory = createAction(actions.INVENTORY_GET_ALL, () => {
	//Last modified by Michel T. On 01/03/23
	(dispatch, getState, config) =>
		axios
			.get(`${config.restAPIUrl}/inventory`)
			.then((suc) => dispatch(refreshInventory(suc.data)));
});

export default handleActions(
	{
		[actions.INVENTORY_GET_ALL]: (state, action) => {
			return {
				...state,
				all: action.payload,
			};
		},
	},

	defaultState
);
