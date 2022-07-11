import * as productDuck from '../ducks/products'
import Avatar from '@material-ui/core/Avatar'
import Checkbox from '@material-ui/core/Checkbox'
import Divider from '@material-ui/core/Divider'
import { EnhancedTableToolbar } from '../components/Table'
import Grid from '@material-ui/core/Grid'
import ImageIcon from '@material-ui/icons/Image'
import List from '@material-ui/core/List'
import ListItem from '@material-ui/core/ListItem'
import ListItemAvatar from '@material-ui/core/ListItemAvatar'
import ListItemIcon from '@material-ui/core/ListItemIcon'
import ListItemText from '@material-ui/core/ListItemText'
import { makeStyles } from '@material-ui/core/styles'
import Paper from '@material-ui/core/Paper'
import ProductDeleteModal from '../components/Products/ProductDeleteModal'
import ProductFormModal from '../components/Products/ProductFormModal'
import React, { useCallback, useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'

const useStyles = makeStyles((theme) => ({
  root: {
    width: '100%',
    backgroundColor: theme.palette.background.paper,
  },
  medium: {
    width: theme.spacing(4),
    height: theme.spacing(4),
  }
}))

const ProductLayout = (props) => {
  const classes = useStyles()
  const dispatch = useDispatch()

  const products = useSelector(state => state.products.all)
  const isFetched = useSelector(state => state.products.fetched)
  const removeProducts = useCallback(ids => { dispatch(productDuck.removeProducts(ids)) }, [dispatch])
  const saveProducts = useCallback(product => { dispatch(productDuck.saveProducts(product)) }, [dispatch])

  useEffect(() => {
    if (!isFetched) {
      dispatch(productDuck.findProducts())
    }
  }, [dispatch, isFetched])

  const [isCreateOpen, setCreateOpen] = React.useState(false)
  const [isEditOpen, setEditOpen] = React.useState(false)
  const [isDeleteOpen, setDeleteOpen] = React.useState(false)
  const toggleCreate = () => {
    setCreateOpen(true)
  }
  const toggleEdit = () => {
    setEditOpen(true)
  }
  const toggleDelete = () => {
    setDeleteOpen(true)
  }
  const toggleModals = (resetChecked) => {
    setCreateOpen(false)
    setDeleteOpen(false)
    setEditOpen(false)
    if (resetChecked) {
      setChecked([])
    }
  }
  const [checked, setChecked] = React.useState([])
  const handleToggle = (value) => () => {
    const currentIndex = checked.indexOf(value)
    const newChecked = [...checked]

    if (currentIndex === -1) {
      newChecked.push(value)
    } else {
      newChecked.splice(currentIndex, 1)
    }
    setChecked(newChecked)
  }

  return (
    <Grid container>
      <Grid item xs={12}>
        <Paper>
          <EnhancedTableToolbar
            numSelected={checked.length}
            title='Products'
            toggleCreate={toggleCreate}
            toggleDelete={toggleDelete}
            toggleEdit={toggleEdit}
          />
          <List dense disablePadding className={classes.root}>
            {products.map((value, index) =>
              <React.Fragment key={index}>
                <Divider/>
                <ListItem button onClick={handleToggle(value)}>
                  <ListItemIcon>
                    <Checkbox
                      onChange={handleToggle(value)}
                      checked={checked.indexOf(value) !== -1}
                    />
                  </ListItemIcon>
                  <ListItemAvatar>
                    <Avatar className={classes.medium}>
                      <ImageIcon/>
                    </Avatar>
                  </ListItemAvatar>
                  <ListItemText primary={value.name}/>
                </ListItem>
              </React.Fragment>
            )}
          </List>
        </Paper>
        <ProductFormModal
          title='Create'
          formName='productCreate'
          isDialogOpen={isCreateOpen}
          handleDialog={toggleModals}
          handleProduct={saveProducts}
          initialValues={{}}
        />
        <ProductFormModal
          title='Edit'
          formName='productEdit'
          isDialogOpen={isEditOpen}
          handleDialog={toggleModals}
          handleProduct={saveProducts}
          initialValues={checked[0]}
        />
        <ProductDeleteModal
          isDialogOpen={isDeleteOpen}
          handleDelete={removeProducts}
          handleDialog={toggleModals}
          initialValues={checked.map(check => check.id)}
        />
      </Grid>
    </Grid>
  )
}

export default ProductLayout
