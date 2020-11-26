import React, {useEffect, useState} from "react";

interface SelectorProps {
    list: string[]
    submit: (option: string) => void
    last?: boolean
}

const Selector: React.FC<SelectorProps> = (props) => {

    const [selected, setSelected] = useState<string>('')

    const checkAndSubmit = (option: string) => {
        if (option !== '') props.submit(option)
        else window.alert("Please select an option");
    }

    useEffect(() => {
        setSelected(props.list[0])
    }, []) //eslint-disable-line

    return (
        <>
            <select onChange={(event) => setSelected(event.target.value)}>
                {props.list.map((type, idx) => {
                        return (
                            <option key={idx} value={type}>
                                {type}
                            </option>
                        )
                    }
                )}
            </select>
            <button onClick={() => checkAndSubmit(selected)}>{!props.last ? "Submit" : "Submit and Order"}</button>
        </>
    )
}


export default Selector
