function SearchBar({
  value,
  onChange,
  placeholder = "Search..."
}) {
  return (
    <div className="search-bar">
      <input
        className="search-input"
        type="text"
        value={value}
        onChange={(e) =>
          onChange(e.target.value)
        }
        placeholder={placeholder}
      />
    </div>
  );
}

export default SearchBar;