function Loader({
  text = "Loading..."
}) {
  return (
    <div className="loader">
      <h3>{text}</h3>
    </div>
  );
}

export default Loader;