import Button from '@material-ui/core/Button'
import Dialog from '@material-ui/core/Dialog'
import DialogActions from '@material-ui/core/DialogActions'
import DialogContent from '@material-ui/core/DialogContent'
import DialogTitle from '@material-ui/core/DialogTitle'
import Grid from '@material-ui/core/Grid'
import React from 'react'
import TextField from '../Form/TextField'
import { Field, Form, Formik } from 'formik'

class InventoryFormModal extends React.Component {
    render() {
      const {
        formName,
        handleDialog,
        handleInventory,
        title,
        initialValues
      } = this.props
      return (
        <Dialog
          open={this.props.isDialogOpen}
          maxWidth='sm'
          fullWidth={true}
          onClose={() => { handleDialog(false) }}
        >
          <Formik
            initialValues={initialValues}
            onSubmit={values => {
              handleInventory(values)
              handleDialog(true)
            }}>
            {helpers =>
              <Form
                noValidate
                autoComplete='off'
                id={formName}
              >
                <DialogTitle id='alert-dialog-title'>
                  {`${title} Inventory`}
                </DialogTitle>
                <DialogContent>
                  <Grid container>
                    <Grid item xs={12} sm={12}>
                      <Field
                        custom={{ variant: 'outlined', fullWidth: true, }}
                        name='name'
                        label='Name'
                        component={TextField}
                      />
                    </Grid>
                  </Grid>
                </DialogContent>
                <DialogActions>
                  <Button onClick={() => { handleDialog(false) }} color='secondary'>Cancel</Button>
                  <Button
                    disableElevation
                    variant='contained'
                    type='submit'
                    form={formName}
                    color='secondary'
                    disabled={!helpers.dirty}>
                    Save
                  </Button>
                </DialogActions>
              </Form>
            }
          </Formik>
        </Dialog>
      )
    }
}

export default InventoryFormModal