import { getIn } from 'formik'
import MuiTextField from '@material-ui/core/TextField'
import React from 'react'

const fieldToTextField = ({
  backgroundColor,
  custom,
  disabled,
  field: { onBlur: fieldOnBlur, ...field },
  form: { errors, isSubmitting, touched },
  helperText,
  onBlur,
  variant,
  warning,
  ...props
}) => {
  const dirty = getIn(touched, field.name)
  const fieldError = getIn(errors, field.name)
  const showError = dirty && !!fieldError
  return {
    variant: variant,
    error: showError,
    helperText: showError ? fieldError : warning ?? helperText,
    disabled: disabled ?? isSubmitting,
    onBlur: (event) => onBlur ?? fieldOnBlur(event ?? field.name),
    ...custom,
    ...field,
    ...props,
  }
}

export const TextField = ({ children, ...props }) =>
  <MuiTextField {...fieldToTextField(props)}>
    {children}
  </MuiTextField>


export default TextField

TextField.displayName = 'FormikTextField'
TextField.tabIndex = 0