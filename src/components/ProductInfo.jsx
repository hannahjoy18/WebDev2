function ProductInfo() {
    const product = {
      name: "AquaFlask Tumbler",
      price: 899,
      color: "Black",
    };
  
    return (
      <div className="component-box">
        <h2>Product Info</h2>
        <p>Name: {product.name}</p>
        <p>Price: ₱{product.price}</p>
        <p>Color: {product.color}</p>
      </div>
    );
  }
  
  export default ProductInfo;
  