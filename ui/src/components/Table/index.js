import AddIcon from '@material-ui/icons/Add'
import Checkbox from '@material-ui/core/Checkbox'
import classNames from 'classnames'
import DeleteIcon from '@material-ui/icons/Delete'
import EditIcon from '@material-ui/icons/Create'
import FilterListIcon from '@material-ui/icons/FilterList'
import IconButton from '@material-ui/core/IconButton'
import PropTypes from 'prop-types'
import React from 'react'
import TableCell from '@material-ui/core/TableCell'
import TableHead from '@material-ui/core/TableHead'
import TableRow from '@material-ui/core/TableRow'
import TableSortLabel from '@material-ui/core/TableSortLabel'
import Toolbar from '@material-ui/core/Toolbar'
import Tooltip from '@material-ui/core/Tooltip'
import Typography from '@material-ui/core/Typography'
import { lighten, makeStyles } from '@material-ui/core/styles'

export function stableSort(array, comparator) {
  const stabilizedThis = array.map((el, index) => [el, index])
  stabilizedThis.sort((a, b) => {
    const order = comparator(a[0], b[0])
    if (order !== 0) return order
    return a[1] - b[1]
  })
  return stabilizedThis.map((el) => el[0])
}

export function descendingComparator(a, b, orderBy) {
  if (b[orderBy] < a[orderBy]) {
    return -1
  }
  if (b[orderBy] > a[orderBy]) {
    return 1
  }
  return 0
}

export function getComparator(order, orderBy) {
  return order === 'desc'
    ? (a, b) => descendingComparator(a, b, orderBy)
    : (a, b) => -descendingComparator(a, b, orderBy)
}

export function EnhancedTableHead(props) {
  const { onSelectAllClick, order, orderBy, numSelected, rowCount, onRequestSort, headCells } = props
  const createSortHandler = (property) => (event) => {
    onRequestSort(event, property)
  }
  return (
    <TableHead>
      <TableRow>
        <TableCell padding='checkbox'>
          <Checkbox
            indeterminate={numSelected > 0 && numSelected < rowCount}
            checked={rowCount > 0 && numSelected === rowCount}
            onChange={onSelectAllClick}
          />
        </TableCell>
        {headCells.map((headCell) =>
          <TableCell
            key={headCell.id}
            align={headCell.align}
            padding={headCell.disablePadding ? 'none' : 'default'}
            sortDirection={orderBy === headCell.id ? order : false}
          >
            <TableSortLabel
              active={orderBy === headCell.id}
              direction={orderBy === headCell.id ? order : 'asc'}
              onClick={createSortHandler(headCell.id)}
            >
              {headCell.label}
            </TableSortLabel>
          </TableCell>
        )}
      </TableRow>
    </TableHead>
  )
}

EnhancedTableHead.propTypes = {
  classes: PropTypes.object.isRequired,
  numSelected: PropTypes.number.isRequired,
  onRequestSort: PropTypes.func.isRequired,
  onSelectAllClick: PropTypes.func.isRequired,
  order: PropTypes.oneOf(['asc', 'desc']).isRequired,
  orderBy: PropTypes.string.isRequired,
  rowCount: PropTypes.number.isRequired,
}

const useToolbarStyles = makeStyles((theme) => ({
  root: {
    paddingLeft: theme.spacing(2),
    paddingRight: theme.spacing(1),
    backgroundColor: theme.palette.background.paper,
    minHeight: 40,
  },
  highlight:
    theme.palette.type === 'light'
      ? {
        color: theme.palette.secondary.main,
        backgroundColor: lighten(theme.palette.secondary.light, 0.85),
      }
      : {
        color: theme.palette.text.primary,
        backgroundColor: theme.palette.secondary.dark,
      },
  title: {
    flex: '1 1 100%',
  },
}))

export const EnhancedTableToolbar = (props) => {
  const classes = useToolbarStyles()
  const { numSelected, toggleCreate, toggleDelete, toggleEdit } = props

  return (
    <Toolbar disableGutters
      className={classNames(classes.root, {
        [classes.highlight]: numSelected > 0,
      })}
    >
      {numSelected > 0
        ? <Typography className={classes.title} color='inherit' variant='subtitle1' component='div'>
          {numSelected} selected
        </Typography>
        : <Typography className={classes.title} variant='subtitle1' id='tableTitle' component='div'>
          {props.title}
        </Typography>
      }
      {numSelected > 0
        ? <React.Fragment>
          {numSelected === 1
            ? <Tooltip arrow title='Edit'>
              <IconButton size='small' onClick={toggleEdit} aria-label='edit'>
                <EditIcon/>
              </IconButton>
            </Tooltip>
            : null
          }
          <Tooltip arrow title='Delete'>
            <IconButton size='small' onClick={toggleDelete} aria-label='delete'>
              <DeleteIcon/>
            </IconButton>
          </Tooltip>
        </React.Fragment>
        : <React.Fragment>
          <Tooltip arrow title='Create'>
            <IconButton size='small' onClick={toggleCreate} aria-label='create new'>
              <AddIcon/>
            </IconButton>
          </Tooltip>
          <Tooltip arrow title='Filter list'>
            <IconButton size='small' aria-label='filter list'>
              <FilterListIcon/>
            </IconButton>
          </Tooltip>
        </React.Fragment>
      }
    </Toolbar>
  )
}

EnhancedTableToolbar.propTypes = {
  numSelected: PropTypes.number.isRequired,
}
