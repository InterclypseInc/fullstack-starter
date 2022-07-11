import { useEffect, useRef, useState } from 'react'

/**
 * For use when you need more granular comparisons
 * @param val
 * @returns {boolean}
 */
export const useCompare = (val) => {
  const prevVal = usePrevious(val)
  return prevVal !== val
}

/**
 * Used internally by the useCompare hook
 * @param value
 * @returns {undefined}
 */
const usePrevious = (value) => {
  const ref = useRef()
  useEffect(() => {
    ref.current = value
  })
  return ref.current
}

/**
 * For use when you need to load initial data
 * @param func
 */
export const useMountEffect = (func) => { useEffect(func, []) }

/**
 * Toggle Dark Mode
 */
export const useDarkMode = () => {
  const [theme, setTheme] = useState('light')
  const setMode = mode => {
    localStorage.setItem('theme', mode)
    setTheme(mode)
  }

  const toggleTheme = () => {
    if (theme === 'light') {
      setMode('dark')
    } else {
      setMode('light')
    }
  }

  useEffect(() => {
    const localTheme = localStorage.getItem('theme')
    window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches && !localTheme ?
      setMode('dark') :
      localTheme ?
        setTheme(localTheme) :
        setMode('light')
  }, [])

  return [theme, toggleTheme]
}