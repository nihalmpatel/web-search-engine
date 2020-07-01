/*
 * This file is part of React-SearchKit.
 * Copyright (C) 2018 CERN.
 *
 * React-SearchKit is free software; you can redistribute it and/or modify it
 * under the terms of the MIT License; see LICENSE file for more details.
 */

import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { ResultsList } from '../ResultsList';
import { ResultsGrid } from '../ResultsGrid';
import { ShouldRender } from '../ShouldRender';

export default class ResultsMultiLayout extends Component {
  renderResultsList() {
    return <ResultsList />;
  }

  render() {
    const { loading, totalResults } = this.props;
    return (
      <ShouldRender
        condition={!loading && totalResults > 0}
      >
        {this.renderResultsList()}
      </ShouldRender>
    );
  }
}

ResultsMultiLayout.propTypes = {
  totalResults: PropTypes.number.isRequired,
  currentLayout: PropTypes.string,
};

ResultsMultiLayout.defaultProps = {
  currentLayout: null,
};
