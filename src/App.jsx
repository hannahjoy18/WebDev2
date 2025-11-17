import "./App.css"; 
import ProfileCard from "./components/ProfileCard";
import ProfileList from "./components/ProfileList";
import StepCounter from "./components/StepCounter";
import ProductInfo from "./components/ProductInfo";

function App() {
  return (
    <>
      <h1>My React Components</h1>

      <ProfileCard />
      <ProfileList />
      <StepCounter />
      <ProductInfo />
    </>
  );
}

export default App;
