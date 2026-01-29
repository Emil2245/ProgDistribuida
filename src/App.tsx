import {Routes, Route} from 'react-router-dom'

import NavBar from './components/NavBar'
import Home from './pages/Home'
import BooksPage from './pages/BooksPage'
import BooksDetallePage from './pages/BooksDetallePage'

function App() {

  return (
      <>
          <NavBar/>

          <Routes>
              <Route path="/" element={<Home/>}/>
              <Route path="/books" element={<BooksPage/>}/>
              <Route path="/books-detalle" element={<BooksDetallePage/>}/>
          </Routes>
      </>
  )
}

export default App
