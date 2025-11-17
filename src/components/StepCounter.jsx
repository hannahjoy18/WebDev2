import { useState } from "react";

function StepCounter() {
  const [steps, setSteps] = useState(0);

  return (
    <div className="component-box">
      <h2>Step Counter</h2>
      <p>Steps: {steps}</p>
      <button onClick={() => setSteps(steps + 1)}>Add Step</button>
    </div>
  );
}

export default StepCounter;
