import React, { useEffect, useState } from "react";

import {
	DataGrid,
	GridToolbarContainer,
	GridToolbarExport,
	gridClasses,
} from "@mui/x-data-grid";
import Snackbar from "@mui/material/Snackbar";
import Stack from "@mui/material/Stack";
import IconButton from "@mui/material/IconButton";
import DeleteIcon from "@mui/icons-material/Delete";

// Import server url (named import)
import { SERVER_URL } from "../constants.js";

import AddCar from "./AddCar.js";
import EditCar from "./EditCar.js";

// Export to csv
function CustomToolbar() {
	return (
		<GridToolbarContainer className={gridClasses.toolbarContainer}>
			<GridToolbarExport />
		</GridToolbarContainer>
	);
}

function Carlist() {
	// declare a state called cars and the initial value is an empty array
	const [cars, setCars] = useState([]);

	const [open, setOpen] = useState(false);

	// useEffect(() => {
	// 	fetch(SERVER_URL + "api/cars")
	// 		.then((response) => response.json())
	// 		.then((data) => setCars(data._embedded.cars))
	// 		.catch((err) => console.error(err));
	// }, []);

	// get cars
	const fetchCars = () => {
		// Read the token from the session storage and include it to Authorization header
		const token = sessionStorage.getItem("jwt");

		fetch(SERVER_URL + "api/cars", { headers: { Authorization: token } })
			.then((response) => response.json())
			.then((data) => setCars(data._embedded.cars))
			.catch((err) => console.error(err));
	};

	//  delete car
	const onDelClick = (url) => {
		if (window.confirm("Are you sure to delete?")) {
			const token = sessionStorage.getItem("jwt");

			fetch(url, { method: "DELETE", headers: { Authorization: token } })
				.then((response) => {
					if (response.ok) {
						fetchCars();
						setOpen(true);
					} else {
						alert("Something went wrong!");
					}
				})
				.catch((err) => console.error(err));
		}
	};

	useEffect(() => {
		fetchCars();
	}, []);

	// update car
	const updateCar = (car, link) => {
		const token = sessionStorage.getItem("jwt");

		fetch(link, {
			method: "PUT",
			headers: {
				"Content-Type": "application/json",
				Authorization: token,
			},
			body: JSON.stringify(car),
		})
			.then((response) => {
				if (response.ok) {
					fetchCars();
				} else {
					alert("Something went wrong!");
				}
			})
			.catch((err) => console.error(err));
	};

	const columns = [
		{ field: "brand", headerName: "Brand", width: 200 },
		{ field: "model", headerName: "Model", width: 200 },
		{ field: "color", headerName: "Color", width: 200 },
		{ field: "buildYear", headerName: "Year", width: 150 },
		{ field: "price", headerName: "Price", width: 150 },
		{
			field: "_links.car.href",
			headerName: "",
			sortable: false,
			filterable: false,
			renderCell: (row) => <EditCar data={row} updateCar={updateCar} />,
		},
		{
			field: "_links.self.href",
			headerName: "",
			sortable: false,
			filterable: false,
			renderCell: (row) => (
				// <button onClick={() => onDelClick(row.id)}>Delete</button>

				<IconButton onClick={() => onDelClick(row.id)}>
					<DeleteIcon color="error" />
				</IconButton>
			),
		},
	];

	// Add a new car
	const addCar = (car) => {
		const token = sessionStorage.getItem("jwt");

		fetch(SERVER_URL + "api/cars", {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
				Authorization: token,
			},
			body: JSON.stringify(car),
		})
			.then((response) => {
				if (response.ok) {
					fetchCars();
				} else {
					alert("Something went wrong!");
				}
			})
			.catch((err) => console.error(err));
	};

	// to logout
	// const logout = () => {
	// 	sessionStorage.removeItem("jwt");
	// 	setAuth(false);
	// };

	return (
		<React.Fragment>
			<Stack mt={2} mb={2}>
				<AddCar addCar={addCar} />
			</Stack>

			<div style={{ height: 500, width: "100%" }}>
				{/* <table>
				<tbody>
					{cars.map((car, index) => (
						<tr key={index}>
							<td>{car.brand}</td>
							<td>{car.model}</td>
							<td>{car.color}</td>
							<td>{car.buildYear}</td>
							<td>{car.price}</td>
						</tr>
					))}
				</tbody>
			</table> */}

				<DataGrid
					rows={cars}
					columns={columns}
					disableSelectionOnClick={true}
					getRowId={(row) => row._links.self.href}
					components={{ Toolbar: CustomToolbar }}
				/>

				<Snackbar
					open={open}
					autoHideDuration={2000}
					onClose={() => setOpen(false)}
					message="Car deleted"
				/>
			</div>
		</React.Fragment>
	);
}

export default Carlist;
