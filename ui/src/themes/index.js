export const darkTheme = {
  logo: 'url(/logo.png)',
  palette: {
    type: 'dark',
    primary: {
      main: '#676767'
    },
    secondary: {
      main: '#f26522',
    },
  },
  overrides: {
    MuiCssBaseline: {
      '@global': {
        '*::-webkit-scrollbar': {
          width: '0.5em'
        },
        '*::-webkit-scrollbar-track': {
          '-webkit-box-shadow': 'inset 0 0 6px rgba(0,0,0,0.00)',
          borderRadius: '10px',
        },
        '*::-webkit-scrollbar-thumb': {
          borderRadius: '10px',
          backgroundColor: 'rgba(0, 0, 0, .25)',
          outline: '1px solid slategrey'
        }
      }
    }
  }
}

export const lightTheme = {
  logo: 'url(/logo.png)',
  palette: {
    type: 'light',
    primary: {
      main: '#e7e7e7'
    },
    secondary: {
      main: '#f26522'
    },
  },
  overrides: {
    MuiCssBaseline: {
      '@global': {
        '*::-webkit-scrollbar': {
          width: '0.5em'
        },
        '*::-webkit-scrollbar-track': {
          '-webkit-box-shadow': 'inset 0 0 6px rgba(0,0,0,0.00)',
          borderRadius: '10px',
        },
        '*::-webkit-scrollbar-thumb': {
          borderRadius: '10px',
          backgroundColor: 'rgba(0, 0, 0, .25)',
          outline: '1px solid slategrey'
        }
      }
    }
  }
}