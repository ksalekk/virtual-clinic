import React from 'react'
import './generics.css'

const ItemsList = ({ tableStructure }) => {
	const { data, cols, actions } = tableStructure;

	if (!tableStructure.data) {
		return (<h3> Loading data... </h3>)
	}
	return (
		<div className='ItemsList'>
			<table className='table-list'>
				<thead>
					<tr>
						<th scope="col">#</th>

						{cols.map((col, idx) => (
							<th scope='col' key={idx}>
								{col.name}
							</th>
						))}

						{actions && <th scope="col">Actions</th>}
					</tr>
				</thead>

				<tbody>
					{data.map((item, idx) => (
						<tr scope='row' key={item['id']}>
							<th>{idx}</th>

							{cols.map((col, idx) => (
								<td key={idx}>
									{item[col.property]}
								</td>
							))}

							{tableStructure['actions'] &&
								<td scope="col">{tableStructure['actions'].map((action, idx) => (
									<div
										key={idx}
										className='actions'
										onClick={() => action.handler(item.id)}
									>
										{/* {action['icon']} */}
										{React.createElement(action['icon'], {})}
									</div>
								))}
								</td>
							}
						</tr>
					))}
				</tbody>
			</table>
		</div>
	)
}

export default ItemsList