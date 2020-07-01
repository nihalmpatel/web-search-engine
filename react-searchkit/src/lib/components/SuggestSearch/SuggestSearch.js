/*
 * This file is part of React-SearchKit.
 * Copyright (C) 2018 CERN.
 *
 * React-SearchKit is free software; you can redistribute it and/or modify it
 * under the terms of the MIT License; see LICENSE file for more details.
 */

import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { Label } from 'semantic-ui-react';
import { ShouldRender } from '../ShouldRender';

export default class SuggestSearch extends Component {
  constructor(props) {
    super(props);
    this.renderElement = props.renderElement || this._renderElement;
  }

  _renderElement(suggestedSearch) {
    return <Label color={'blue'}>{suggestedSearch}</Label>;
  }

  render() {
    const { loading, suggestedSearch } = this.props;
    return (
      <ShouldRender condition={!loading && suggestedSearch !== undefined && suggestedSearch !== null && suggestedSearch.trim() !== ""}>
        {this.renderElement(suggestedSearch)}
      </ShouldRender>
    );
  }
}

SuggestSearch.propTypes = {
  loading: PropTypes.bool.isRequired,
  suggestedSearch: PropTypes.string.isRequired,
  renderElement: PropTypes.func,
};

SuggestSearch.defaultProps = {
  renderElement: null,
};
